package at.ac.tuwien.big.we16.ue2.model;

/**
 * Created by dominik on 16.04.2016.
 */
public class User {

    private int userID;
    private String forename, lastname, email, password;
    private double credit;

    public User(){}

    public User(int userID, String forename, String lastname, String email, String password, double credit) {
        this.userID = userID;

        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.credit = credit;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {

        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getForename() {

        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
