package ac.at.tuwien.wmpm.coordinator.configuration;

import ac.at.tuwien.wmpm.coordinator.routes.IncomingRequestRoute;
import ac.at.tuwien.wmpm.coordinator.routes.MainRoute;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Configuration
public class CamelConfiguration {

  @Autowired
  private MainRoute mainRoute;

  @Autowired
  private IncomingRequestRoute incomingRequestRoute;

  @Bean
  public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception {
    SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
    camelContext.addRoutes(incomingRequestRoute);

    return camelContext;
  }
}
