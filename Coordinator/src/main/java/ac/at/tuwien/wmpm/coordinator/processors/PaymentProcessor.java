package ac.at.tuwien.wmpm.coordinator.processors;

import ac.at.tuwien.wmpm.domain.model.Payment;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
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
        // request if needed
        Request request = exchange.getIn().getHeader(RestletConstants.RESTLET_REQUEST, Request.class);

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);

        if (exchange.getIn().getHeader("mail") != null || exchange.getIn().getHeader("amount") == null) {
            String mail = (String) exchange.getIn().getHeader("mail");
            String amountStr = (String) exchange.getIn().getHeader("amount");

            Payment payment = new Payment();
            payment.setUserMail(mail);

            try {
                int amount = Integer.parseInt(amountStr);
                payment.setAmount(amount);
            }
            catch(NumberFormatException e) {
                response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                exchange.getOut().setBody(response);
                return;
            }

            exchange.getIn().setBody(payment, Payment.class);

//            response.setStatus(Status.SUCCESS_OK);
//            response.setEntity("Payment received from user["+userId+"]!", MediaType.TEXT_HTML);
            //exchange.getOut().setBody(response); // cant set response, cant be serialized, duh
//            exchange.getOut().setBody("Payment received from user["+mail+"]!");
            return;
        }

        response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        exchange.getOut().setBody(response);
    }
}
