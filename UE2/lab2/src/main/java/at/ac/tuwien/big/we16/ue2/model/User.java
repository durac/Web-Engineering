package at.ac.tuwien.big.we16.ue2.model;

/**
 * Created by dominik on 16.04.2016.
 */
public class User {

    private int userID, running, won, lost;
    private String forename, lastname, email, password;
    private double credit;

    public User(){}

    public User(int userID, String forename, String lastname, String email, String password, double credit, int running, int won, int lost) {
        this.userID = userID;

        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.credit = credit;
    }


    public void decreaseRunning() {
        this.running--;
    }

    public void decreaseWon() {
        this.won--;
    }

    public int getWon() {
        return won;
    }

    public int getLost() {
        return lost;
    }

    public int getRunning() {
        return running;
    }

    public void decreaseLost() {
        this.lost--;
    }

    public void increaseRunning() {
        this.running++;
    }

    public void increaseWon() {
        this.won++;
    }

    public void increaseLost() {
        this.lost++;
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

    public String getCreditString() {
        String result = String.format("%.2f", credit);
        return result;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void increaseCredit(double amount) {
        this.credit += amount;
    }
}
