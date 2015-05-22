package ac.at.tuwien.wmpm.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dietl_ma on 21/05/15.
 */
@Entity
public class Category {

  @Id
  @NotNull
  @Column(unique = true)
  private String name;

  @ManyToMany
  private List<Expert> experts = new ArrayList<Expert>();

  public Category() {}
  
  public Category(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Expert> getExperts() {
    return experts;
  }

  public void setExperts(List<Expert> experts) {
    this.experts = experts;
  }
  
  @Override
  public String toString() {
    return "Category{"
        + "name="
        + name
        + ", experts={"
        + experts + "}}";
  }
}
