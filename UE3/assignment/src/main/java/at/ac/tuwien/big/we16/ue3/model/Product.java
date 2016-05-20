package at.ac.tuwien.big.we16.ue3.model;

import at.ac.tuwien.big.we16.ue3.exception.InvalidBidException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id
    private String id;
    @Column(nullable=false)
    private String name;
    @Column
    private String image;
    @Column
    private String imageAlt;
    @Column(nullable=false)
    private Date auctionEnd;
    @Enumerated(value = EnumType.STRING)
    private ProductType type;
    @Column
    private int year;
    @Column
    private String producer;
    @Column
    private boolean expired;
    @OneToMany(mappedBy="product", fetch=FetchType.EAGER)
    private List<RelatedProduct> relatedProducts = new ArrayList<>();
    @OneToMany(mappedBy="product", fetch=FetchType.EAGER)
    private List<Bid> bids = new ArrayList<>();

    public Product(String id, String name, Date auctionEnd) {
        this.id = id;
        this.name = name;
        this.auctionEnd = auctionEnd;
    }

    public Product(String id, String name, String image, String imageAlt, Date auctionEnd, ProductType type, int year, String producer, boolean expired) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageAlt = imageAlt;
        this.auctionEnd = auctionEnd;
        this.type = type;
        this.year = year;
        this.producer = producer;
        this.expired = expired;
    }

    public Product() {
    }

    public Bid getHighestBid() {
        Bid highest = null;
        int highestAmount = 0;
        for (Bid bid : this.bids) {
            if (bid.getAmount() > highestAmount) {
                highest = bid;
            }
        }
        return highest;
    }

    public boolean hasAuctionEnded() {
        return this.getAuctionEnd().before(new Date());
    }

    public void addBid(Bid bid) throws InvalidBidException {
        this.bids.add(bid);
    }

    public boolean hasExpired() {
        return expired;
    }

    public void setExpired() {
        this.expired = true;
    }

    public Set<User> getUsers() {
        Set<User> users = this.bids.stream().map(Bid::getUser).collect(Collectors.toSet());
        return users;
    }

    public boolean hasBids() {
        return this.bids.size() > 0;
    }

    public boolean isValidBidAmount(int amount) {
        return !this.hasBids() || this.getHighestBid().getAmount() < amount;
    }

    public boolean hasBidByUser(User user) {
        for (Bid bid : this.bids) {
            if (bid.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public Date getAuctionEnd() {
        return auctionEnd;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<RelatedProduct> getRelatedProducts() {
        return relatedProducts;
    }

}
