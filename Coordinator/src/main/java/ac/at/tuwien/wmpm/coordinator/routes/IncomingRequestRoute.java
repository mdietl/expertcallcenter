package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.coordinator.processors.EnrichWithCategoriesProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.IncomingRequestProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.SaveIncomingRequestProcessor;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.lang3.StringUtils;
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

    logger.info("Incoming Request Route called...");
    
    // consume mail
    from(mailCredentials + "&delete=false&unseen=true&consumer.delay=10000")
        .routeId("RouteMailPoller")
        .log("from mail server")
        .process(incomingRequestProcessor)
        .to("direct:incomingRequest")
        .log("to direct:incomingRequest");

    // send message to accounting app to validate request
    from("direct:incomingRequest")
        .routeId("RouteIncomingRequestBuilder")
        .marshal()
        .json(JsonLibrary.Jackson)
        .to("rabbitmq://localhost/expertCallCenterExchange?queue=incomingRequestValidation&routingKey=incomingRequestValidation&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
        .log("to rabbitmq:incomingRequestValidation");

    // consume validation result
    from(
        "rabbitmq://localhost/expertCallCenterExchange?queue=incomingRequestValidationResponse&exchangeType=topic&durable=true&autoDelete=false")
        .routeId("RouteMailEnricher")
        .log("from rabbitmq:incomingRequestValidationResponse")
        .unmarshal()
        .json(JsonLibrary.Jackson, IncomingRequest.class)
        .choice()
          .when(simple("${body.valid} == true"))
            .log("incomingRequestValidationResponse body is valid")
            .process(enrichWithCategoriesProcessor)
            .log("incomingRequest enriched by tags")
            // send confirmation mail
            .wireTap("direct:incomingRequestConfirmation")
            .end()
            // .process(saveIncomingRequestProcessor)
            .to("jpa:" + IncomingRequest.class.getCanonicalName())
            .log("incomingRequest saved to db")
            // TODO: Foward messge to expert application
            .to("direct:redirectRequest")
            .log("to direct:redirectRequest")
          .otherwise().log("incomingRequestValidationResponse body is not valid")
            .setHeader("Subject", constant("Expert Callcenter WMPM"))
            .setHeader("To", simple("${body.mail}"))
            // .setBody("Your request was not valid"))
            .transform().simple("Your request was not valid!\n\n Your question:\n//${body.question}//")
            .to(smtpCredentials);

    // send confirmation mail
    from("direct:incomingRequestConfirmation")
        .log("from incoming request confirmation")
        .setHeader("Subject", constant("Expert Callcenter WMPM"))
        .setHeader("To", simple("${body.mail}"))
        .transform()
        .simple("Your request is valid!\n\n Your question:\n//${body.question}//")
        .to(smtpCredentials);
    
    // send message to experts app
    from("direct:redirectRequest")
        .routeId("RouteRedirectRequest")
        .marshal()
        .json(JsonLibrary.Jackson)
        .to("rabbitmq://localhost/expertCallCenterExchange?queue=redirectRequest&routingKey=redirectRequest&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
        .log("to rabbitmq:redirectRequest");
    
    // consume experts response
    from("rabbitmq://localhost/expertCallCenterExchange?queue=expertsResponse&exchangeType=topic&durable=true&autoDelete=false")
        .routeId("RouteExperts")
        .log("from rabbitmq:expertsResponse")
        .unmarshal()
        .json(JsonLibrary.Jackson, IncomingRequest.class)
        .to("jpa:" + IncomingRequest.class.getCanonicalName())
        .choice()
            .when(simple("${body.experts.size} > 0"))
                .log("send messages to expert")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        IncomingRequest ir = (IncomingRequest) exchange.getIn().getBody();

                        exchange.getIn().setHeader("To", StringUtils.join(ir.getExperts(), ","));
                        exchange.getIn().setHeader("Subject", "[" + ir.getId() + "] Expert Callcenter WMPM");
                    }
                })
                .transform()
                .simple("Please answer this question:\n\n//${body.question}//")
                .to(smtpCredentials)
            .otherwise().log("no expert available");


  }
}
