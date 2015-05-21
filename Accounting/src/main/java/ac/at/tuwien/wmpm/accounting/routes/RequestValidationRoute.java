package ac.at.tuwien.wmpm.accounting.routes;

import ac.at.tuwien.wmpm.accounting.processors.RequestValidationProcessor;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Georg on 17.05.2015.
 */
@Service
public class RequestValidationRoute extends RouteBuilder {

    @Autowired
    private RequestValidationProcessor requestValidationProcessor;

    private static final Logger logger = LoggerFactory.getLogger(RequestValidationRoute.class);

    @Override
    public void configure() throws Exception {

        from("rabbitmq://localhost/expertCallCenterExchange?queue=incomingRequestValidation&exchangeType=topic&durable=true&autoDelete=false")
            .routeId("RequestValidationRoute")
            .log("from rabbitmq:incomingRequestValidation")
            .unmarshal().json(JsonLibrary.Jackson, IncomingRequest.class)
            .process(requestValidationProcessor)
            .marshal().json(JsonLibrary.Jackson)
            .to("rabbitmq://localhost/expertCallCenterExchange?queue=incomingRequestValidationResponse&exchangeType=topic&durable=true&autoDelete=false")
            .log("to rabbitmq:incomingRequestValidationResponse");
    }

}
