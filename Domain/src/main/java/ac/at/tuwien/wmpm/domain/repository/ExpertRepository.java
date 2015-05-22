package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.Expert;

/**
 * Created by dietl_ma on 21/04/15.
 */
public interface ExpertRepository extends IGenericRepository<Expert, String> {

  public Expert findByEmail(String email);
}
