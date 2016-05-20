package at.ac.tuwien.big.we16.ue3.model;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    private String id;
    @Column(nullable=false)
    private int amount;
    @ManyToOne(optional=false)
    private User user;
    @ManyToOne(optional=false)
    private Product product;

    public Bid(String id, int centAmount, User user, Product product) {
        this.id=id;
        amount = centAmount;
        this.user = user;
        this.product = product;
    }

    public Bid() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public float getConvertedAmount() {
        float convertedAmount = (float)this.amount;
        return convertedAmount / 100;
    }

    public User getUser() {
        return user;
    }

    public boolean isBy(User user) {
        return this.user.equals(user);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
