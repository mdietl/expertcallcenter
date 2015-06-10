package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.domain.model.Invoice;
import ac.at.tuwien.wmpm.domain.model.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Georg on 10.06.2015.
 */
@Service
public class InvoiceSendingRoute extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceSendingRoute.class);

    @Value("${smtp.mail.credentials}")
    private String smtpCredentials;

    @Override
    public void configure() throws Exception {

        from("rabbitmq://localhost/expertCallCenterExchange?queue=InvoiceRoute&routingKey=InvoiceRoute&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
            .routeId("InvoiceSendingRoute")
            .log("from rabbitmq:InvoiceRoute")
            .unmarshal()
            .json(JsonLibrary.Jackson, Invoice.class)
            .to("direct:SendInvoiceMail")
            .log("to direct:SendInvoiceMail");

        from("direct:SendInvoiceMail")
            .routeId("SendInvoiceMail")
            .log("from direct:SendInvoiceMail")
            .removeHeaders("*")
            .setHeader("Subject", constant("Expert Callcenter Invoice"))
            .setHeader("To", simple("${body.mail}"))
            .transform()
            .simple("Click this link to pay:\n\n http://localhost:8888/paymentService/${body.mail}/${body.amount} \n\nExpertcallcenter")
            .to(smtpCredentials);

    }
}
