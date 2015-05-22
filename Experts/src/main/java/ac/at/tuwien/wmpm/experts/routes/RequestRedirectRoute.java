package ac.at.tuwien.wmpm.experts.routes;

import ac.at.tuwien.wmpm.experts.messaging.MessageHandler;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Anna Sadriu on 22.05.2015.
 */
@Service
public class RequestRedirectRoute extends RouteBuilder {

  @Autowired
  private MessageHandler messageHandler;

  private static final Logger logger = LoggerFactory.getLogger(RequestRedirectRoute.class);

  @Override
  public void configure() throws Exception {
    logger.info("Experts Message handling...");
    from(
        "rabbitmq://localhost/expertCallCenterExchange?queue=redirectRequest&routingKey=redirectRequest&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
        .routeId("RequestValidationRoute")
        .log("from rabbitmq:redirectRequest")
        .unmarshal()
        .json(JsonLibrary.Jackson, IncomingRequest.class)
        .process(messageHandler)
        .marshal()
        .json(JsonLibrary.Jackson)
        .to("rabbitmq://localhost/expertCallCenterExchange?queue=expertsResponse&routingKey=expertsResponse&exchangeType=topic&durable=true&autoDelete=false&BridgeEndpoint=true")
        .log("to rabbitmq:expertsResponse");
  }

}
