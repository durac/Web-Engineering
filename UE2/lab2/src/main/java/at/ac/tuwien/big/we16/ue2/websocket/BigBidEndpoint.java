package at.ac.tuwien.big.we16.ue2.websocket;

import at.ac.tuwien.big.we16.ue2.service.NotifierService;
import at.ac.tuwien.big.we16.ue2.model.User;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.RemoteEndpoint;



/**
 * This endpoint listens on the /socket URL.
 */
@ServerEndpoint(value="/socket", configurator = BigBidConfigurator.class)
public class BigBidEndpoint {
    private final NotifierService notifierService;

    public BigBidEndpoint(NotifierService notifierService) {
        this.notifierService = notifierService;
    }

    /**
     * When a new WebSocket connection is established, we register both the
     * socket session and the associated HTTP session with the notifier service.
     */
    @OnOpen
    public void onOpen(Session socketSession, EndpointConfig config) {
        this.notifierService.register(socketSession, (HttpSession) config.getUserProperties().get(HttpSession.class.getName()));
    }
    
    /**
     * receives String messages in the form "new_bid product_id price"
     * and "expired product_id"
     * 
     * sends String messages in the form "product_id user_name price"
     * @param message
     * @param s
     */
    @OnMessage
	public void onMessage(String message) {
    	
    	
    
    	Map<Session, HttpSession> clients = notifierService.getClients();
    	for(Session p : clients.keySet()) {				
			RemoteEndpoint.Basic endpoint = p.getBasicRemote();
			try {
				endpoint.sendText(message);
			}
			catch(IOException e) {
				//maybe do something else here
				e.printStackTrace();
			}
		}
    	/*
    	String[] tokens = message.split(" ");
		if(tokens[0].equals("new_bid")) {
			
			String product_id = tokens[1];
			String price = tokens[2];
						
			Map<Session, HttpSession> clients = notifierService.getClients();
			//HttpSession bidderSession = clients.get(s);
			//User bidder = (User) bidderSession.getAttribute("user");
			//String user_name = bidder.getForename() + " " + bidder.getLastname();
			
			String send_message = "new_bid" + " " + product_id + " "
			+ user_name + " " + price;
			for(Session p : clients.keySet()) {				
				RemoteEndpoint.Basic endpoint = p.getBasicRemote();
				try {
					endpoint.sendText(send_message);
				}
				catch(IOException e) {
					//maybe do something else here
					e.printStackTrace();
				}
			}				
		}
		else if(tokens[0].equals("expired")) {
			String product_id = tokens[1];
			String user_id = tokens[2];
			String account = tokens[3];
			
		}
		*/				
	}
    
    
    /**
     * When a socket connection is closed, we remove its session from the
     * notifier service.
     */
    @OnClose
    public void onClose(Session socketSession) {
        this.notifierService.unregister(socketSession);
    }
}