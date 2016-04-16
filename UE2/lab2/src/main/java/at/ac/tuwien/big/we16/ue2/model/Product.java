package at.ac.tuwien.big.we16.ue2.model;

import java.util.Date;

/**
 * Created by dominik on 16.04.2016.
 */
public class Product {

    private String productID;
    private String description;
    private double price;
    private Date expiration_date;
    private User highest_bidder;
    private String image;

    public Product(){}

    public Product(String productID, String description, double price, Date expiration_date, String image) {
        this.productID = productID;
        this.description = description;
        this.price = price;
        this.expiration_date = expiration_date;
        this.image = image;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpirationDate() {
        return expiration_date;
    }

    public void setExpirationDate(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public User getHighest_bidder() {
        return highest_bidder;
    }

    public void setHighest_bidder(User highest_bidder) {
        this.highest_bidder = highest_bidder;
    }
}
