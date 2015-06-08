package ac.at.tuwien.wmpm.coordinator.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Georg on 08.06.2015.
 */
@Component
public class PaymentProcessor implements Processor {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.info("Exchange: " + exchange);

        //exchange.getIn().setBody(ir, IncomingRequest.class);
    }
}
