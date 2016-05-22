package at.ac.tuwien.big.we16.ue3.service;

import at.ac.tuwien.big.we16.ue3.exception.UserNotFoundException;
import at.ac.tuwien.big.we16.ue3.model.Bid;
import at.ac.tuwien.big.we16.ue3.model.Product;
import at.ac.tuwien.big.we16.ue3.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class NotifierService {

    private static final String BIGBIDBOARDURL = "https://lectures.ecosio.com/b3a/api/v1/bids";

    private static Map<Session, HttpSession> clients = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor;

    public NotifierService() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.startExpiredProductsThread();
    }

    public void register(Session socketSession, HttpSession httpSession) {
        clients.put(socketSession, httpSession);
    }

    public void unregister(Session userSession) {
        clients.remove(userSession);
    }

    public void stop() {
        this.executor.shutdown();
    }

    public void notifyAllAboutBid(Bid bid) {
        this.sendToAllSockets(new NewBidVisitor(bid));
    }

    public void notifyReimbursement(User user) {
        this.sendToAllSockets(new ReimbursementVisitor(user));
    }

    private void startExpiredProductsThread() {
        executor.scheduleAtFixedRate(() -> {
            Collection<Product> newlyExpiredProducts = ServiceFactory.getProductService().checkProductsForExpiration();
            if (!newlyExpiredProducts.isEmpty()) {
                NotifierService.this.sendToAllSockets(new ExpiredProductsVisitor(newlyExpiredProducts));
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void sendToAllSockets(Visitor visitor) {
        for (Map.Entry<Session, HttpSession> client : clients.entrySet()) {
            try {
                Object sessionUser = client.getValue().getAttribute("user");
                if (sessionUser != null) {
                    User user = ServiceFactory.getUserService().getUserByEmail(((User) sessionUser).getEmail());
                    String text = visitor.getText(user);
                    if (text != null) {
                        client.getKey().getAsyncRemote().sendText(text);
                    }
                }
            } catch (IllegalStateException | UserNotFoundException e) {
                clients.remove(client.getKey());
            }
        }
    }

    public String sendAuctionToBigBidBoard(Product p){
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        HttpPost request;
        String id="-1";
        try{
            request = new HttpPost(BIGBIDBOARDURL);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            StringEntity json =new StringEntity("{\"name\":\""+p.getHighestBid().getUser().getFullName()+"\",\"product\":\""+p.getName()+"\"," +
                    "\"price\":\""+p.getHighestBid().getConvertedAmount()+"\",\"date\":\""+sdf.format(p.getAuctionEnd())+"\"} ");

            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            request.setEntity(json);

            response = client.execute(request);

            if(response.getStatusLine().getStatusCode()!=200){
                System.err.println("Fehler bei der Verwendung von BigBidBoard");
                return id;
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            JsonObject jsonObject = new Gson().fromJson(result.toString(), JsonObject.class);
            id = jsonObject.get("id").getAsString();
            System.out.println(id);
        } catch (IOException e) {
            System.err.println("Fehler bei der Verwendung von BigBidBoard");
        }
        return id;
    }

    public void postTweet(String uuid, Product p){
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("GZ6tiy1XyB9W0P4xEJudQ")
                    .setOAuthConsumerSecret("gaJDlW0vf7en46JwHAOkZsTHvtAiZ3QUd2mD1x26J9w")
                    .setOAuthAccessToken("1366513208-MutXEbBMAVOwrbFmZtj1r4Ih2vcoHGHE2207002")
                    .setOAuthAccessTokenSecret("RMPWOePlus3xtURWRVnv1TgrjTyK7Zk33evp4KKyA");
            Twitter twitter = new TwitterFactory(cb.build()).getInstance();

            TwitterStatusMessage message = new TwitterStatusMessage(p.getHighestBid().getUser().getFullName(),uuid,p.getAuctionEnd());
            Status status = twitter.updateStatus(message.getTwitterPublicationString());
            System.out.println(status.getText());
        } catch (TwitterException e) {
            System.err.println("Fehler bei der Verwendung von TwitterAPI");
        }
    }

    private interface Visitor {
        String getText(User user);
    }

    private class NewBidVisitor implements Visitor {
        private final String text;

        public NewBidVisitor(Bid bid) {
            this.text = "{\"type\": \"newBid\", \"id\": \"" + bid.getProduct().getId() + "\", \"userFullName\": \"" + bid.getUser().getFullName() + "\", \"amount\": " + bid.getConvertedAmount() + "}";
        }

        public String getText(User user) {
            return this.text;
        }
    }

    private class ExpiredProductsVisitor implements Visitor {
        private final String expiredProductsText;

        public ExpiredProductsVisitor(Collection<Product> newlyExpiredProducts) {
            this.expiredProductsText = String.join(",", newlyExpiredProducts.stream().map(product -> "\"" + product.getId() + "\"").collect(Collectors.toList()));
        }

        public String getText(User user) {
            return "{\"type\": \"expiredProducts\", \"running\": " + user.getRunningAuctionsCount() + ", \"lost\": " + user.getLostAuctionsCount() + ", \"won\": " + user.getWonAuctionsCount() + ", \"balance\": " + user.getConvertedBalance() + ", \"expiredProducts\": [" + this.expiredProductsText + "]}";
        }
    }

    private class ReimbursementVisitor implements Visitor {
        private final User user;

        private ReimbursementVisitor(User user) {
            this.user = user;
        }

        @Override
        public String getText(User user) {
            if (this.user.equals(user)) {
                return "{\"type\": \"reimbursement\", \"balance\": " + this.user.getConvertedBalance() + "}";
            }
            return null;
        }
    }
}
