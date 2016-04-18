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

    private static ArrayList<User> users;
    private static ArrayList<Product> products;

    @Override
    public void init() throws ServletException {
        products = new ArrayList<Product>();
        users = new ArrayList<User>();
        User u1 = new User(1,"John","Doe","john@doe.com","john",1500);
        User u2 = new User(2,"Jane","Doe","jane@doe.com","jane",1500);
        User u3 = new User(3,"Dominik","Schwarz","dominik@schwarz.com","dominik",1500);
        User u4 = new User(4,"Stefan","Neubauer","stefan@neubauer.com","stefan",1500);
        User u5 = new User(5,"Raquel","Sima","raquel@sima.com","raquel",1500);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = null;
        for(User u: users){
            if(u.getEmail().equals(email) && u.getPassword().equals(password)){
                user = u;
                break;
            }
        }
        if(user==null){
            resp.sendRedirect("/");
            return;
        }

        HttpSession session = req.getSession(true);

        JSONDataLoader.Music[] music = JSONDataLoader.getMusic();
        JSONDataLoader.Movie[] movies = JSONDataLoader.getFilms();
        JSONDataLoader.Book[] books = JSONDataLoader.getBooks();

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

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

}
