package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.Entity;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Entity
public class User extends Account {

  private int sentQuestions;

  private int receivedAnswers;

  private int paidAnswers;

    public int getInvoicedAnswers() {
        return invoicedAnswers;
    }

    public void setInvoicedAnswers(int invoicedAnswers) {
        this.invoicedAnswers = invoicedAnswers;
    }

    private int invoicedAnswers;

  public User() {}

  public User(String email) {
    super.setEmail(email);
  }
  
  public int getSentQuestions() {
    return sentQuestions;
  }

  public void setSentQuestions(int sentQuestions) {
    this.sentQuestions = sentQuestions;
  }

  public int getReceivedAnswers() {
    return receivedAnswers;
  }

  public void setReceivedAnswers(int receivedAnswers) {
    this.receivedAnswers = receivedAnswers;
  }

  public int getPaidAnswers() {
    return paidAnswers;
  }

  public void setPaidAnswers(int paidAnswers) {
    this.paidAnswers = paidAnswers;
  }

    @Override
    public String toString() {
        return "User{" +
                "sentQuestions=" + sentQuestions +
                ", receivedAnswers=" + receivedAnswers +
                ", paidAnswers=" + paidAnswers +
                ", invoicedAnswers=" + invoicedAnswers +
                '}';
    }

}
