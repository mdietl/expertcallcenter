package ac.at.tuwien.wmpm.coordinator.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Service
public class RabbitMessagingRoute extends RouteBuilder {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void configure() throws Exception {

    from("direct:sendRabbitObjectMessage").process(new Processor() {
      @Override
      public void process(Exchange exchange) throws Exception {

      }
    });
  }
}
