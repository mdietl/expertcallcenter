package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.coordinator.processors.EnrichWithCategoriesProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.IncomingRequestProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.SaveIncomingRequestProcessor;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private SaveIncomingRequestProcessor saveIncomingRequestProcessor;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${incomingrequest.mail.credentials}")
    private String mailCredentials;

    @Value("${smtp.mail.credentials}")
    private String smtpCredentials;


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
            .log("to direct:incomingRequest");

        //send message to accounting app to validate request
        from("direct:incomingRequest")
            .routeId("RouteIncomingRequestBuilder")
            .marshal().json(JsonLibrary.Jackson)
            .to("rabbitmq://localhost/expertCallCenterExchange?queue=incomingRequestValidation&routingKey=incomingRequestValidation&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
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
                    .log("incomingRequest enriched by tags")
                    //send confirmation mail
                    .wireTap("direct:incomingRequestConfirmation").end()
                    //.process(saveIncomingRequestProcessor)
                    .to("jpa:" + IncomingRequest.class.getCanonicalName())
                    .log("incomingRequest saved to db")
                    
                    /TODO: Foward messge to expert application 
                    // this should copy the exchange ?! (used wireTap) 
                    //.multicast().to("direct:forwardToExpertApp").end()
                    .wireTap("direct:forwardToExpertApp").end()
                   // log("forwared message to ecpert application / tried also with mulicast")
                    log("forwared message to ecpert application")
                .otherwise()
                    .log("incomingRequestValidationResponse body is not valid")
                    .setHeader("Subject", constant("Expert Callcenter WMPM"))
                    .setHeader("To", simple("${body.mail}"))
                    //.setBody("Your request was not valid"))
                    .transform().simple("Your request was not valid!\n\n Your question:\n//${body.question}//")
                    .to(smtpCredentials);

        //send confirmation mail
        from("direct:incomingRequestConfirmation")
            .log("incomingRequestValidationResponse body is not valid")
            .setHeader("Subject", constant("Expert Callcenter WMPM"))
            .setHeader("To", simple("${body.mail}"))
                    //.setBody("Your request was not valid"))
            .transform().simple("Your request is valid!\n\n Your question:\n//${body.question}//")
            .to(smtpCredentials);
    }
}
