package ac.at.tuwien.wmpm.accounting.processors;

import ac.at.tuwien.wmpm.domain.model.Invoice;
import ac.at.tuwien.wmpm.domain.model.User;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Georg on 10.06.2015.
 */
@Component
public class InvoiceProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceProcessor.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("processing exchange: " + exchange);

        User u = exchange.getIn().getBody(User.class);

        Invoice i = new Invoice();
        i.setMail(u.getEmail());

        if (u.getSentQuestions() > u.getInvoicedAnswers()) {
            i.setAmount(u.getSentQuestions() - u.getPaidAnswers());
            u.setInvoicedAnswers(u.getInvoicedAnswers() + i.getAmount());
//            userRepository.save(u);
        } else {
            i.setAmount(0);
        }

        exchange.getIn().setBody(i, Invoice.class);
        logger.info("outgoing exchange: " + exchange);
    }

}
