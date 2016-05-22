package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;

import at.ac.tuwien.big.we16.ue2.websocket.BigBidEndpoint;
import at.ac.tuwien.big.we16.ue2.service.NotifierService;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import java.io.IOException;
import java.util.Map;

/**
 * Created by dominik on 24.04.2016.
 */
@WebServlet(name = "Bid", urlPatterns = { "/BidServlet" })
public class BidServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double bid = Double.parseDouble(req.getParameter("new-price"));
        HttpSession session = req.getSession(true);
        Product p = (Product) session.getAttribute("product");
        User u = (User) session.getAttribute("user");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject jo = new JsonObject();
        if(p.getPrice()<bid){
            User lastBidder = p.getHighestBidder();
            if (lastBidder != null){
                lastBidder.increaseCredit(p.getPrice());
                LoginServlet.updateUser(lastBidder);
            }
            p.setPrice(bid);
            p.setHighestBidder(u);
            u.increaseRunning();
            u.increaseCredit(-bid);
            LoginServlet.updateUser(u);
            LoginServlet.updateProduct(p);
            jo.addProperty("error",false);
            jo.addProperty("running",u.getRunning());
            jo.addProperty("credit",u.getCredit());
            
            /*
            JsonObject newBid = new JsonObject();
            newBid.addProperty("type", "newBid");
            newBid.addProperty("bidder", u.getForename() + " " + u.getLastname());
            newBid.addProperty("price", req.getParameter("new-price"));
            */
            String type = "new_bid";
            String product_id = p.getProductID();
            String forename = u.getForename();
            String lastname =  u.getLastname();
           	double price = p.getPrice();
            
            NotifierService notifierService = new NotifierService();
            Map<Session, HttpSession> clients = notifierService.getClients();

            BigBidEndpoint websocket = new BigBidEndpoint(notifierService);
           
            String message = type + " " + product_id + " " + forename
            		+ " " + lastname + " "  + price;
            
            websocket.onMessage(message);
            
        }
        else{
            jo.addProperty("error",true);
        }
        resp.getWriter().write(jo.toString());
    }

}
