package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.model.Product;
import at.ac.tuwien.big.we16.ue2.model.User;
import at.ac.tuwien.big.we16.ue2.productdata.JSONDataLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dominik on 16.04.2016.
 */
@WebServlet(name = "Login", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet{



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User("John","Doe",email,password, 1500);

        JSONDataLoader.Music[] music = JSONDataLoader.getMusic();
        JSONDataLoader.Movie[] movies = JSONDataLoader.getFilms();
        JSONDataLoader.Book[] books = JSONDataLoader.getBooks();

        ArrayList<Product> products = new ArrayList<Product>();
        for(JSONDataLoader.Music m: music) {
            Product product = new Product(m.getProductID(),m.getAlbum_name(), 0, m.getExpirationDate(), m.getImg());
            products.add(product);
        }
        for(JSONDataLoader.Movie m: movies){
            Product product = new Product(m.getProductID(), m.getTitle(), 0, m.getExpirationDate(), m.getImg());
            products.add(product);
        }
        for(JSONDataLoader.Book b: books){
            Product product = new Product(b.getProductID(), b.getTitle(), 0, b.getExpirationDate(), b.getImg());
            products.add(product);
        }

        session.setAttribute("user",user);
        session.setAttribute("products",products);
        resp.sendRedirect("/views/overview.jsp");
    }
}
