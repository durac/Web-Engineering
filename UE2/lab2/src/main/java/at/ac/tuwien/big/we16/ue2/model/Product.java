package at.ac.tuwien.big.we16.ue2.model;

import java.text.SimpleDateFormat;
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

    public String getImageName() {
        return image;
    }

    public void setImageName(String image) {
        this.image = image;
    }

    public String getExpirationDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss,SSS");
        return sdf.format(expiration_date);
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

    public String getPriceString() {
        return String.format("%.2f",price);
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

    public User getHighestBidder() {
        return highest_bidder;
    }

    public String getHighestBidderString() {
        if(highest_bidder!=null) {
            return highest_bidder.getForename()+" "+highest_bidder.getLastname();
        }
        return "Keine Gebote";
    }

    public void setHighestBidder(User highest_bidder) {
        this.highest_bidder = highest_bidder;
    }

    public String getProductClass(User user){
        String r = "";
        if(user.equals(highest_bidder))
            r = "highlight";
        else if(expiration_date.before(new Date()))
            r = "expired";
        return r;
    }
}
