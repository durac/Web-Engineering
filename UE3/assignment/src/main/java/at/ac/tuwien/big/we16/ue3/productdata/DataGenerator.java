package at.ac.tuwien.big.we16.ue3.productdata;

import at.ac.tuwien.big.we16.ue3.model.Product;
import at.ac.tuwien.big.we16.ue3.model.ProductType;
import at.ac.tuwien.big.we16.ue3.model.User;
import at.ac.tuwien.big.we16.ue3.service.ProductService;
import at.ac.tuwien.big.we16.ue3.service.ServiceFactory;
import at.ac.tuwien.big.we16.ue3.service.UserService;

import java.util.Date;

public class DataGenerator {

    public void generateData() {
        generateUserData();
        generateProductData();
        insertRelatedProducts();
    }

    private void generateUserData() {
        UserService userService = ServiceFactory.getUserService();
        User u1 = new User("1","Herr","Dominik","Schwarz","dominik@schwarz.com","schwarz",new Date(1994,7,1),1500,0,0,0);
        userService.createUser(u1);
        User u2 = new User("2","Frau","Raquel","Sima","raquel@sima.com","sima",new Date(1995,8,1),1500,0,0,0);
        userService.createUser(u2);
        User u3 = new User("3","Herr","Stefan","Neubauer","stefan@neubauer.com","neubauer",new Date(1992,9,1),1500,0,0,0);
        userService.createUser(u3);
        User u4 = new User("4","Frau","Jane","Doe","jane.doe@example.com","password",new Date(1990,9,1),1500,0,0,0);
        userService.createUser(u4);
    }

    private void generateProductData() {
        ProductService productService = ServiceFactory.getProductService();
        JSONDataLoader.Music[] music = JSONDataLoader.getMusic();
        JSONDataLoader.Movie[] movies = JSONDataLoader.getFilms();
        JSONDataLoader.Book[] books = JSONDataLoader.getBooks();
        for(JSONDataLoader.Music m: music) {
            Product product = new Product(m.getProductID(),m.getAlbum_name(), m.getImg(),"Das ist der alt Text",m.getExpirationDate(), ProductType.ALBUM, Integer.parseInt(m.getYear()), m.getArtist(), m.getExpirationDate().after(new Date()));
            productService.createProduct(product);
        }
        for(JSONDataLoader.Movie m: movies){
            Product product = new Product(m.getProductID(),m.getTitle(), m.getImg(),"Das ist der alt Text",m.getExpirationDate(), ProductType.FILM, Integer.parseInt(m.getYear()), m.getDirector(), m.getExpirationDate().after(new Date()));
            productService.createProduct(product);
        }
        for(JSONDataLoader.Book b: books){
            Product product = new Product(b.getProductID(),b.getTitle(), b.getImg(),"Das ist der alt Text",b.getExpirationDate(), ProductType.BOOK, Integer.parseInt(b.getYear()), b.getAuthor(), b.getExpirationDate().after(new Date()));
            productService.createProduct(product);
        }
    }

    private void insertRelatedProducts() {
        // TODO load related products from dbpedia and write them to the database
    }
}
