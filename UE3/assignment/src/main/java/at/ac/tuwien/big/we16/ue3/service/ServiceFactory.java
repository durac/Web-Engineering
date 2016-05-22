package at.ac.tuwien.big.we16.ue3.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ServiceFactory {
    private static ProductService productService;
    private static NotifierService notifierService;
    private static ComputerUserService computerUserService;
    private static UserService userService;
    private static BidService bidService;

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
        }
        return emf;
    }

    public static ProductService getProductService() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }

    public static NotifierService getNotifierService() {
        if (notifierService == null) {
            notifierService = new NotifierService();
        }
        return notifierService;
    }

    public static ComputerUserService getComputerUserService() {
        if (computerUserService == null) {
            computerUserService = new ComputerUserService(
                    getBidService(),
                    getProductService()
            );
        }
        return computerUserService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static BidService getBidService() {
        if (bidService == null) {
            bidService = new BidService();
        }
        return bidService;
    }
}
