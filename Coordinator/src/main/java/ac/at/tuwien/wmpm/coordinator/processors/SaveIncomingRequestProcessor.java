package ac.at.tuwien.wmpm.coordinator.processors;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import ac.at.tuwien.wmpm.domain.repository.IncomingRequestRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dietl_ma on 21/05/15.
 */
@Component
public class SaveIncomingRequestProcessor implements Processor {

    @Autowired
    IncomingRequestRepository incomingRequestRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        IncomingRequest ir = (IncomingRequest) exchange.getIn().getBody();
        incomingRequestRepository.save(ir);
    }
}
