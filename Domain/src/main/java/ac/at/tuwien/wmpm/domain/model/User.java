package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.Entity;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Entity
public class User extends Account {

    private Integer sentQuestions;

    private Integer receivedAnswers;

    private Integer paidAnswers;

    public User() {
        super();
        sentQuestions = 0;
        receivedAnswers = 0;
        paidAnswers = 0;
    }


    public Integer getSentQuestions() {
        return sentQuestions;
    }

    public void setSentQuestions(Integer sentQuestions) {
        this.sentQuestions = sentQuestions;
    }

    public Integer getReceivedAnswers() {
        return receivedAnswers;
    }

    public void setReceivedAnswers(Integer receivedAnswers) {
        this.receivedAnswers = receivedAnswers;
    }

    public Integer getPaidAnswers() {
        return paidAnswers;
    }

    public void setPaidAnswers(Integer paidAnswers) {
        this.paidAnswers = paidAnswers;
    }
}
