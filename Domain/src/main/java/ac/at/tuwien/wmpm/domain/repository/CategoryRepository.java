package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dietl_ma on 21/05/15.
 */
@Repository
@Transactional
public interface CategoryRepository extends IGenericRepository<Category, String> {
  public Category findByName(String name);
}
