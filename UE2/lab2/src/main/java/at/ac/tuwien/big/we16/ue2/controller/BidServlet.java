package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
            jo.addProperty("credit",u.getCreditString());
            jo.addProperty("bid",p.getPriceString());
            jo.addProperty("bidder",p.getHighestBidderString());
        }
        else{
            jo.addProperty("error",true);
        }
        resp.getWriter().write(jo.toString());
    }

}
