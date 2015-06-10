package ac.at.tuwien.wmpm.accounting.configuration;

import ac.at.tuwien.wmpm.accounting.routes.InvoiceRoute;
import ac.at.tuwien.wmpm.accounting.routes.PaymentReceiverRoute;
import ac.at.tuwien.wmpm.accounting.routes.RequestValidationRoute;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Georg on 17.05.2015.
 */
@Configuration
public class CamelConfiguration {

  @Autowired
  private RequestValidationRoute requestValidationRoute;

  @Autowired
  private PaymentReceiverRoute paymentReceiverRoute;

  @Autowired
  private InvoiceRoute invoiceRoute;

  @Bean
  public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception {
    SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
    camelContext.addRoutes(requestValidationRoute);
    camelContext.addRoutes(paymentReceiverRoute);
    camelContext.addRoutes(invoiceRoute);

    return camelContext;
  }
}
