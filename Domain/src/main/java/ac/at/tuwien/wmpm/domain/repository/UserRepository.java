package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Repository
@Transactional
public interface UserRepository extends IGenericRepository<User, String> {

  public User findByEmail(String email);
}
