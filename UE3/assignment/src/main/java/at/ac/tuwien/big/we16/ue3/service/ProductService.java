package at.ac.tuwien.big.we16.ue3.service;

import at.ac.tuwien.big.we16.ue3.exception.ProductNotFoundException;
import at.ac.tuwien.big.we16.ue3.model.Bid;
import at.ac.tuwien.big.we16.ue3.model.Product;
import at.ac.tuwien.big.we16.ue3.model.RelatedProduct;
import at.ac.tuwien.big.we16.ue3.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductService {

    private EntityManager em;

    public ProductService() {
        this.em = ServiceFactory.getEntityManager();
    }

    public void createProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.refresh(product);
        em.getTransaction().commit();
    }


    public Collection<Product> getAllProducts() {
        Query q = em.createQuery("select p from Product p");
        List<Product> list = q.getResultList();
        return list;
    }

    public Product getProductById(String id) throws ProductNotFoundException {
        Product p = em.find(Product.class,id);
        if(p == null){
            throw new ProductNotFoundException();
        }
        return p;
    }

    public Collection<Product> checkProductsForExpiration() {
        Collection<Product> newlyExpiredProducts = new ArrayList<>();
        for (Product product : this.getAllProducts()) {
            if (!product.hasExpired() && product.hasAuctionEnded()) {
                product.setExpired();
                newlyExpiredProducts.add(product);
                if (product.hasBids()) {
                    Bid highestBid = product.getHighestBid();
                    for (User user : product.getUsers()) {
                        user.decrementRunningAuctions();
                        if (highestBid.isBy(user)) {
                            user.incrementWonAuctionsCount();
                        }
                        else {
                            user.incrementLostAuctionsCount();
                        }
                        ServiceFactory.getUserService().updateUser(user);
                    }
                }
                this.updateProduct(product);
            }
        }
        return newlyExpiredProducts;
    }

    public void createRelatedProduct(RelatedProduct product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }
}
