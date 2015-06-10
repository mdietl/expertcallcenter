package ac.at.tuwien.wmpm.accounting.routes;

import ac.at.tuwien.wmpm.accounting.processors.InvoiceProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ac.at.tuwien.wmpm.domain.model.User;

/**
 * Created by Georg on 10.06.2015.
 */
@Service
public class InvoiceRoute extends RouteBuilder {

    @Autowired
    InvoiceProcessor invoiceProcessor;

    private static final Logger logger = LoggerFactory.getLogger(InvoiceRoute.class);

    @Override
    public void configure() throws Exception {

//        &consumer.query=select u from User u&
        from("jpa://" + User.class.getCanonicalName() +"?consumeDelete=false&consumer.delay=30000")
            .routeId("InvoicePollingRoute")
            .log("from jpa:Users")
            .process(invoiceProcessor).marshal()
            .json(JsonLibrary.Jackson)
            .to("rabbitmq://localhost/expertCallCenterExchange?queue=InvoiceRoute&routingKey=InvoiceRoute&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
            .log("to rabbitmq:InvoiceRoute");
    }
}
