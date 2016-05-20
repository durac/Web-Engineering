package at.ac.tuwien.big.we16.ue3.model;

import javax.persistence.*;

@Entity
public class RelatedProduct {

    @Id
    @GeneratedValue
    private String id;
    @Column(nullable=false)
    private String name;
    @ManyToOne(optional=false)
    private Product product;

    public RelatedProduct() {
    }

    public RelatedProduct(String id, String name, Product product) {
        this.id=id;
        this.name=name;
        this.product=product;
    }

    public RelatedProduct(String name) {
        this.name = name;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
