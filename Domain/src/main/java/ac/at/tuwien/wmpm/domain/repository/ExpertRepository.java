package ac.at.tuwien.wmpm.domain.repository;

import java.util.List;

import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.model.Category;

/**
 * Created by dietl_ma on 21/04/15.
 * Edited by sadrian on 22/05/15.
 */
public interface ExpertRepository extends IGenericRepository<Expert, String> {

  public Expert findByEmail(String email);
  
  public List<Expert> findByCategory(Category category);
}
