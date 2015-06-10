package ac.at.tuwien.wmpm.domain.model;

/**
 * Created by Georg on 10.06.2015.
 */
public class Invoice {

    String mail;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    int amount;

    public Invoice() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "mail='" + mail + '\'' +
                ", amount=" + amount +
                '}';
    }
}
