package ac.at.tuwien.wmpm.accounting.processors;

import ac.at.tuwien.wmpm.domain.model.Payment;
import ac.at.tuwien.wmpm.domain.model.User;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Georg on 09.06.2015.
 */
@Component
public class PaymentReceiverProcessor implements Processor {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PaymentReceiverProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Payment payment = exchange.getIn().getBody(Payment.class);
        logger.info("processing payment: " + payment);

        User u = userRepository.findByEmail(payment.getUserMail());

        if(u == null) {
            logger.warn("user["+payment.getUserMail()+"] doesn't exist");
            return;
        }

        u.setPaidAnswers(u.getPaidAnswers()+payment.getAmount());
        userRepository.save(u);

        logger.info("saved user: " + u);
    }

}
