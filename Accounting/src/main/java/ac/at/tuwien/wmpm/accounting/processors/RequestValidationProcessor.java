package ac.at.tuwien.wmpm.accounting.processors;

import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import ac.at.tuwien.wmpm.domain.model.User;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Georg on 17.05.2015.
 */
@Component
public class RequestValidationProcessor implements Processor {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(RequestValidationProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        IncomingRequest incomingRequest = exchange.getIn().getBody(IncomingRequest.class);

        logger.info("Processing request: " + incomingRequest);

        incomingRequest.setCounter(incomingRequest.getCounter()+1);

        User user = userRepository.findByEmail(incomingRequest.getMail());

        if (user == null) {
            logger.info("user not found. create new user...");
            User newUser = new User();
            newUser.setEmail(incomingRequest.getMail());
            userRepository.save(newUser);
        }

        //do some validation stuff
        incomingRequest.setValid(true);

        logger.info("Processed request["+incomingRequest.getCounter()+"]: " + incomingRequest);
    }
}
