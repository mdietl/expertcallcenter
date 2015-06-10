package ac.at.tuwien.wmpm.accounting.routes;

import ac.at.tuwien.wmpm.accounting.processors.PaymentReceiverProcessor;
import ac.at.tuwien.wmpm.domain.model.Payment;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Georg on 09.06.2015.
 */
@Service
public class PaymentReceiverRoute extends RouteBuilder {

    @Autowired
    PaymentReceiverProcessor paymentReceiverProcessor;

    private static final Logger logger = LoggerFactory.getLogger(PaymentReceiverRoute.class);

    @Override
    public void configure() throws Exception {

        from("rabbitmq://localhost/expertCallCenterExchange?queue=UserPayment&routingKey=UserPayment&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
            .routeId("PaymentReceiverRoute")
            .log("from rabbitmq:UserPayment")
            .unmarshal()
            .json(JsonLibrary.Jackson, Payment.class)
            .process(paymentReceiverProcessor);
    }
}
