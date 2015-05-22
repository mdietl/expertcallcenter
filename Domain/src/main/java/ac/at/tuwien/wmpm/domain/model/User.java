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

  public User() {
    super();
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
    return "User{"
        + "email="
        + getEmail()
        + ", sentQuestions="
        + sentQuestions
        + ", receivedAnswers="
        + receivedAnswers
        + ", paidAnswers="
        + paidAnswers + '}';
  }
}
