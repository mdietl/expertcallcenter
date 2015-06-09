package ac.at.tuwien.wmpm.domain.model;

/**
 * Created by Georg on 09.06.2015.
 */
public class Payment {

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "userId=" + userId +
                '}';
    }
}
