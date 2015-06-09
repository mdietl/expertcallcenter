package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.ExpertResponse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by sadrian on 09/06/15.
 */
@Repository
@Transactional
public interface ExpertResponseRepository extends IGenericRepository<ExpertResponse, String> {

  public ExpertResponse findById(UUID id);
}
