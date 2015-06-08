package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.coordinator.processors.PaymentProcessor;
import org.apache.camel.builder.RouteBuilder;
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

        //from("jetty:http://localhost:8888/paymentService")
        from("restlet:http://localhost:8888/paymentService")
                .routeId("paymentRoute")
                .log("received payment");
    }
}
