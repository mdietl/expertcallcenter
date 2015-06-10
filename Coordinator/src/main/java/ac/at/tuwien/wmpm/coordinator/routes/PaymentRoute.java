package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.coordinator.processors.PaymentProcessor;
import ac.at.tuwien.wmpm.domain.model.Payment;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Georg on 08.06.2015.
 */
@Service
public class PaymentRoute extends RouteBuilder {

    @Autowired
    private PaymentProcessor paymentProcessor;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(PaymentRoute.class);

    @Override
    public void configure() throws Exception {

        from("restlet:http://localhost:8888/paymentService/{mail}/{amount}")
            .routeId("paymentRoute")
            .log("received payment")
            .process(paymentProcessor)
            .marshal()
            .json(JsonLibrary.Jackson)
            .to("rabbitmq://localhost/expertCallCenterExchange?queue=UserPayment&routingKey=UserPayment&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
            .log("to rabbitmq:UserPayment");
    }
}
