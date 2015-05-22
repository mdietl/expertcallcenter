package ac.at.tuwien.wmpm.experts.configuration;

import ac.at.tuwien.wmpm.experts.routes.RequestRedirectRoute;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Anna Sadriu on 22.05.2015.
 */
@Configuration
public class CamelConfiguration {

  @Autowired
  private RequestRedirectRoute requestRedirectRoute;

  @Bean
  public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception {
    SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
    camelContext.addRoutes(requestRedirectRoute);

    return camelContext;
  }
}
