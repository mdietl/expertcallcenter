package ac.at.tuwien.wmpm.coordinator.routes;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ac.at.tuwien.wmpm.coordinator.processors.EnrichWithCategoriesProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.IncomingRequestProcessor;
import ac.at.tuwien.wmpm.coordinator.processors.SaveIncomingRequestProcessor;
import ac.at.tuwien.wmpm.domain.model.ExpertResponse;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import ac.at.tuwien.wmpm.domain.repository.ExpertResponseRepository;
import ac.at.tuwien.wmpm.domain.repository.IncomingRequestRepository;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
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
  IncomingRequestRepository incomingRequestRepository;
  
  @Autowired
  ExpertResponseRepository expertResponseRepository;

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
        .log("check subject")
        .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
              logger.info("Scanning Incoming Requests...");
              
              for(IncomingRequest ir : incomingRequestRepository.findAll()) {
                logger.info("ID: " + ir.getId());
                
                if(exchange.getIn().getHeader("subject").toString().indexOf(ir.getId().toString()) >= 0) {
                  logger.info("BODY: " + exchange.getIn().toString());
                  ExpertResponse er = new ExpertResponse(UUID.randomUUID());
                  er.setTitle("### WMPM Response to [" + ir.getId().toString() + "] ###");
                  er.setMail(parseFrom(exchange.getIn().getHeader("From").toString()));
                  er.setAnswer(exchange.getIn().getBody(String.class));
                  
//                  IncomingRequest req = incomingRequestRepository.findById(ir.getId());
                  er.setIncomingRequest(ir);
                  
                  //TODO should be checked
                  er.setValid(true);

                  logger.info("Expert Response processed, ER: " + er);

                  exchange.getIn().setBody(er, ExpertResponse.class);
                  exchange.getIn().setHeader("Subject", "### WMPM Response to [" + ir.getId().toString() + "] ###");
                  
                  expertResponseRepository.save(er);
                  ir.addAnswer(er);
                }
              }
            }
        })

        .log("New subject: ${header.subject}")
        .choice()
          .when(simple("${header.subject} regex '### WMPM Response to \\[(.*?)\\] ###'"))
            .to("jpa:" + ExpertResponse.class.getCanonicalName())
            .to("direct:returnResponses")
            .log("to direct:returnResponses")
    
          .otherwise()
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
            .to("direct:redirectRequest")
            .log("to direct:redirectRequest")
          .otherwise().log("incomingRequestValidationResponse body is not valid")
            .removeHeaders("*")
            .setHeader("Subject", constant("Expert Callcenter WMPM"))
            .setHeader("To", simple("${body.mail}"))
            // .setBody("Your request was not valid"))
            .transform().simple("Your request was not valid!\n\n Your question:\n//${body.question}//")
            .to(smtpCredentials);

    // send confirmation mail
    from("direct:incomingRequestConfirmation")
        .log("from incoming request confirmation")
        .removeHeaders("*")
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
                .log("send messages to experts")
                .removeHeaders("*")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        IncomingRequest ir = (IncomingRequest) exchange.getIn().getBody();

                        exchange.getIn().setHeader("Bcc", StringUtils.join(ir.getExperts(), ","));
                        exchange.getIn().setHeader("Subject", "[" + ir.getId() + "] Expert Callcenter WMPM");
                    }
                })
                .transform()
                .simple("Please answer this question and use this id in the subject:\n[${body.id}]" +
                        "\n\n''${body.question}''")
                .to(smtpCredentials)
            .otherwise().log("no expert available");
    
    // send answers to back to the user
    from("direct:returnResponses")
        .routeId("RouteExpertResponseBuilder")

        .aggregate(simple("${header.subject}"), new AggregationStrategy() {
              @Override
              public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                if (oldExchange != null) {
                  oldExchange.getIn().removeHeaders("*");
                
                  oldExchange.getIn().setHeader("To", newExchange.getIn().getBody(ExpertResponse.class).getIncomingRequest().getMail());
                  oldExchange.getIn().setHeader("Subject", constant("Reply from Expert Callcenter WMPM"));
                  
                  ExpertResponse oldBody= oldExchange.getIn().getBody(ExpertResponse.class);
                  
                  String oldAnswer= oldExchange.getIn().getBody(ExpertResponse.class).getAnswer();
                  String newAnswer= newExchange.getIn().getBody(ExpertResponse.class).getAnswer();
                  
                  oldAnswer = oldAnswer + "\n\n" + newAnswer;
                  oldBody.setAnswer(oldAnswer);
                  oldExchange.getIn().setBody(oldBody);
                  
                  return oldExchange;
                } else {
                  newExchange.getIn().removeHeaders("*");
                  
                  newExchange.getIn().setHeader("To", newExchange.getIn().getBody(ExpertResponse.class).getIncomingRequest().getMail());
                  newExchange.getIn().setHeader("Subject", constant("Reply from Expert Callcenter WMPM"));
                  
                  return newExchange;
                }
                
              }
            })
        .completionSize(2)

        .transform()
        .simple("You can find the answer(s) of your question below!\n\n ${body.answer} \n\nYour question:\n${body.incomingRequest.question}")
        .to(smtpCredentials);
  }
  
  /**
   * Parse email
   * 
   * @param from
   * @return
   */
  private String parseFrom(String from) {

    Pattern p =
        Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
    Matcher matcher = p.matcher(from);
    ArrayList<String> emails = new ArrayList<String>();
    while (matcher.find()) {
      emails.add(matcher.group());
    }

    return emails.get(0);
  }
}
