package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.coordinator.processors.EnrichWithCategoriesProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.IncomingRequestProcessor;
import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Service
public class IncomingRequestRoute extends RouteBuilder {

    @Autowired
    private IncomingRequestProcessor incomingRequestProcessor;

    @Autowired
    private EnrichWithCategoriesProcessor enrichWithCategoriesProcessor;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${incomingrequest.mail.credentials}")
    private String mailCredentials;


    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(IncomingRequestRoute.class);

    @Override
    public void configure() throws Exception {

        //consume mail
        from(mailCredentials + "&delete=false&unseen=true&consumer.delay=10000")
            .routeId("RouteMailPoller")
            .log("from mail server")
            .process(incomingRequestProcessor)
            .to("direct:incomingRequest")
            .log("to incomingRequest");

        //send message to accounting app to validate request
        from("direct:incomingRequest")
            .routeId("RouteIncomingRequestBuilder")
            .log("from incomingRequest")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {

                    IncomingRequest ir = (IncomingRequest) exchange.getIn().getBody();
                    rabbitTemplate.convertAndSend(CommonRabbitConfiguration.INCOMING_REQUEST_VALIDATION, ir);
                }
            })
            .log("to rabbitmq:incomingRequestValidation");

        //consume validation result
        from("rabbitmq://localhost/expertCallCenterExchange?queue=incomingRequestValidationResponse&exchangeType=topic&durable=true&autoDelete=false")
            .routeId("RouteMailEnricher")
            .log("from rabbitmq:incomingRequestValidationResponse")
            .unmarshal().json(JsonLibrary.Jackson, IncomingRequest.class)
            .choice()
                .when(simple("${body.valid} == true"))
                    .log("incomingRequestValidationResponse body is valid")
                    .process(enrichWithCategoriesProcessor)
                .otherwise()
                    .log("incomingRequestValidationResponse body is not valid");
    }
}
