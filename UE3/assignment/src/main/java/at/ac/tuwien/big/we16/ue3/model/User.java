package at.ac.tuwien.big.we16.ue3.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    private String id;
    @Column(nullable=false)
    private String salutation;
    @Column(nullable=false)
    private String firstname;
    @Column(nullable=false)
    private String lastname;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false)
    private Date date;
    @Column(nullable=false)
    private int balance;
    @Column
    private int runningAuctionsCount;
    @Column
    private int wonAuctionsCount;
    @Column
    private int lostAuctionsCount;

    public User() {
    }

    public User(String id, String salutation, String firstname, String lastname, String email, String password, Date date, int balance, int runningAuctionsCount, int wonAuctionsCount, int lostAuctionsCount) {
        this.id = id;
        this.salutation = salutation;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.date = date;
        this.balance = balance;
        this.runningAuctionsCount = runningAuctionsCount;
        this.wonAuctionsCount = wonAuctionsCount;
        this.lostAuctionsCount = lostAuctionsCount;
    }

    public String getFullName() {
        return this.firstname + " " + this.lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public float getConvertedBalance() {
        float convertedBalance = (float)this.balance;
        return convertedBalance / 100;
    }

    public void decreaseBalance(int amount) {
        this.balance -= amount;
    }

    public void increaseBalance(int amount) {
        this.balance += amount;
    }

    public int getRunningAuctionsCount() {
        return this.runningAuctionsCount;
    }

    public void incrementRunningAuctions() {
        this.runningAuctionsCount++;
    }

    public void decrementRunningAuctions() {
        this.runningAuctionsCount--;
    }

    public int getWonAuctionsCount() {
        return this.wonAuctionsCount;
    }

    public int getLostAuctionsCount() {
        return this.lostAuctionsCount;
    }

    public void incrementLostAuctionsCount() {
        this.lostAuctionsCount++;
    }

    public void incrementWonAuctionsCount() {
        this.wonAuctionsCount++;
    }

    public boolean hasSufficientBalance(int amount) {
        return this.balance >= amount;
    }

    public String getEmail() {
        return email;
    }

    public String getSalutation() {
        return salutation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
