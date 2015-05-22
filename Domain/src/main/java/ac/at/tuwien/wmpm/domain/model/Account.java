package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {

  @Id
  @NotNull
  @Column(unique = true)
  private String email;

  public Account() {}
  
  public Account(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Account{" + "email='" + email + '\'' + '}';
  }
}
