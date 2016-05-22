package at.ac.tuwien.big.we16.ue3.productdata;

import at.ac.tuwien.big.we.dbpedia.api.DBPediaService;
import at.ac.tuwien.big.we.dbpedia.api.SelectQueryBuilder;
import at.ac.tuwien.big.we.dbpedia.vocabulary.DBPedia;
import at.ac.tuwien.big.we.dbpedia.vocabulary.DBPediaOWL;
import at.ac.tuwien.big.we16.ue3.model.Product;
import at.ac.tuwien.big.we16.ue3.model.ProductType;
import at.ac.tuwien.big.we16.ue3.model.RelatedProduct;
import at.ac.tuwien.big.we16.ue3.model.User;
import at.ac.tuwien.big.we16.ue3.service.ProductService;
import at.ac.tuwien.big.we16.ue3.service.ServiceFactory;
import at.ac.tuwien.big.we16.ue3.service.UserService;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DataGenerator {

    public void generateData() {
        generateUserData();
        generateProductData();
        insertRelatedProducts();
    }

    private void generateUserData() {
        UserService userService = ServiceFactory.getUserService();
        User u1 = new User(UUID.randomUUID().toString(),"Herr","Dominik","Schwarz","dominik@schwarz.com","schwarz",new Date(1994,7,1),150000,0,0,0);
        userService.createUser(u1);
        User u2 = new User(UUID.randomUUID().toString(),"Frau","Raquel","Sima","raquel@sima.com","sima",new Date(1995,8,1),150000,0,0,0);
        userService.createUser(u2);
        User u3 = new User(UUID.randomUUID().toString(),"Herr","Stefan","Neubauer","stefan@neubauer.com","neubauer",new Date(1992,9,1),150000,0,0,0);
        userService.createUser(u3);
        User u4 = new User(UUID.randomUUID().toString(),"Frau","Jane","Doe","jane.doe@example.com","password",new Date(1990,9,1),150000,0,0,0);
        userService.createUser(u4);
    }

    private void generateProductData() {
        ProductService productService = ServiceFactory.getProductService();
        JSONDataLoader.Music[] music = JSONDataLoader.getMusic();
        JSONDataLoader.Movie[] movies = JSONDataLoader.getFilms();
        JSONDataLoader.Book[] books = JSONDataLoader.getBooks();
        for(JSONDataLoader.Music m: music) {
            Product product = new Product(m.getProductID(),m.getAlbum_name(), m.getImg(),"Das ist der alt Text",m.getExpirationDate(), ProductType.ALBUM,
                    Integer.parseInt(m.getYear()), m.getArtist(), false);
            productService.createProduct(product);
        }
        for(JSONDataLoader.Movie m: movies){
            Product product = new Product(m.getProductID(),m.getTitle(), m.getImg(),"Das ist der alt Text",m.getExpirationDate(), ProductType.FILM,
                    Integer.parseInt(m.getYear()), m.getDirector(), false);
            productService.createProduct(product);
        }
        for(JSONDataLoader.Book b: books){
            Product product = new Product(b.getProductID(),b.getTitle(), b.getImg(),"Das ist der alt Text",b.getExpirationDate(), ProductType.BOOK,
                    Integer.parseInt(b.getYear()), b.getAuthor(), false);
            productService.createProduct(product);
        }
    }

    private void insertRelatedProducts() {

        if (!DBPediaService.isAvailable()) {
            return;
        }

        for(Product p : ServiceFactory.getProductService().getAllProducts()) {
            String searchName = p.getProducer();
            searchName = searchName.replace(' ','_');

            Resource name = DBPediaService.loadStatements(DBPedia.createResource(searchName));

            SelectQueryBuilder query = null;
            switch(p.getType()){
                case FILM:
                    query = DBPediaService.createQueryBuilder()
                            .setLimit(5)
                            .addWhereClause(RDF.type, DBPediaOWL.Film)
                            .addPredicateExistsClause(FOAF.name)
                            .addWhereClause(DBPediaOWL.director, name)
                            .addFilterClause(RDFS.label, Locale.ENGLISH);
                    break;
                case BOOK:
                    query = DBPediaService.createQueryBuilder()
                            .setLimit(5)
                            .addWhereClause(RDF.type, DBPediaOWL.Book)
                            .addPredicateExistsClause(FOAF.name)
                            .addWhereClause(DBPediaOWL.author, name)
                            .addFilterClause(RDFS.label, Locale.ENGLISH);
                    break;
                case ALBUM:
                    query = DBPediaService.createQueryBuilder()
                            .setLimit(5)
                            .addWhereClause(RDF.type, DBPediaOWL.Album)
                            .addPredicateExistsClause(FOAF.name)
                            .addWhereClause(DBPediaOWL.artist, name)
                            .addFilterClause(RDFS.label, Locale.ENGLISH);
                    break;
            }

            Model products = DBPediaService.loadStatements(query.toQueryString());
            List<String> relatesProducts = DBPediaService.getResourceNames(products, Locale.ENGLISH);
            for(String s: relatesProducts){
                RelatedProduct rp = new RelatedProduct(UUID.randomUUID().toString(),s,p);
                p.addRelatedProduct(rp);
                ServiceFactory.getProductService().createRelatedProduct(rp);
            }
        }
    }

}
