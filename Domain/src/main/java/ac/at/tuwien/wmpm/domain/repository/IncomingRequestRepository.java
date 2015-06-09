package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Repository
@Transactional
public interface IncomingRequestRepository extends IGenericRepository<IncomingRequest, String> {

  public IncomingRequest findById(UUID id);
}
