package ac.at.tuwien.wmpm.domain.model;

/**
 * Created by Georg on 09.06.2015.
 */
public class Payment {

    public Payment() {
    }

    public Payment(String userMail) {
        this.userMail = userMail;
    }

    private String userMail;

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
