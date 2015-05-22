package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Entity
public class Expert extends Account {

  @ManyToMany(mappedBy = "experts", fetch = FetchType.EAGER)
  private List<Category> categories = new ArrayList<Category>();

  public Expert () {}
  
  public Expert (String email) {
    super.setEmail(email);
  }
  
  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }
  
  @Override
  public String toString() {
    return "User{"
        + "email="
        + getEmail()
        + ", categories={"
        + categories + "}}";
  }
}
