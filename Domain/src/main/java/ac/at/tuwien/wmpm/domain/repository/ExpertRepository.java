package ac.at.tuwien.wmpm.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.model.Category;
import org.springframework.data.repository.query.Param;

/**
 * Created by dietl_ma on 21/04/15.
 * Edited by sadrian on 22/05/15.
 */
public interface ExpertRepository extends IGenericRepository<Expert, String> {

  public Expert findByEmail(String email);
  
  //@Query("SELECT e FROM Expert e WHERE e.categories IN (:categories)")
  @Query("SELECT e FROM Expert e INNER JOIN e.categories c WHERE c IN (:categories)")
  public List<Expert> findByCategoriesIn(@Param("categories")List<Category> categories);
}
