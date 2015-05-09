package ac.at.tuwien.wmpm.coordinator.processors;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Component
public class IncomingRequestProcessor implements Processor {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(IncomingRequestProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {

        IncomingRequest ir = new IncomingRequest(UUID.randomUUID());
        ir.setTitle(exchange.getIn().getHeader("Subject").toString());
        ir.setMail(parseFrom(exchange.getIn().getHeader("From").toString()));
        ir.setQuestion(exchange.getIn().getBody(String.class));

        exchange.getIn().setBody(ir, IncomingRequest.class);
    }

    /**
     * Parse email
     * @param from
     * @return
     */
    private String parseFrom(String from) {

        Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(from);
        ArrayList<String> emails = new ArrayList<String>();
        while(matcher.find()) {
            emails.add(matcher.group());
        }

        return emails.get(0);
    }
}
