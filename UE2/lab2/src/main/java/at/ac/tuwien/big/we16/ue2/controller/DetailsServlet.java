package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dominik on 18.04.2016.
 */
@WebServlet(name = "Details", urlPatterns = { "/DetailsServlet" })
public class DetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String productID = req.getParameter("id");
        for(Product p : LoginServlet.getProducts()){
            if(p.getProductID().equals(productID)){
                session.setAttribute("product",p);
                resp.sendRedirect("/views/details.jsp");
                return;
            }
        }
        resp.sendRedirect("/views/overview.jsp");
    }

}
