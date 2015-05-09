package ac.at.tuwien.wmpm.domain.repository;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;

import java.util.UUID;

/**
 * Created by dietl_ma on 09/05/15.
 */
public interface IncomingRequestRepository  extends IGenericRepository<IncomingRequest, String> {

    public IncomingRequest findById(UUID id);
}
