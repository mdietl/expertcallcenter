package ac.at.tuwien.wmpm.coordinator.configuration;

import ac.at.tuwien.wmpm.coordinator.routes.IncomingRequestRoute;
import ac.at.tuwien.wmpm.coordinator.routes.InvoiceSendingRoute;
import ac.at.tuwien.wmpm.coordinator.routes.MainRoute;
import ac.at.tuwien.wmpm.coordinator.routes.PaymentRoute;
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
    private IncomingRequestRoute incomingRequestRoute;

    @Autowired
    private PaymentRoute paymentRoute;

    @Autowired
    private InvoiceSendingRoute invoiceSendingRoute;

    @Bean
    public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception {
        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
        camelContext.addRoutes(incomingRequestRoute);
        camelContext.addRoutes(paymentRoute);
        camelContext.addRoutes(invoiceSendingRoute);

    return camelContext;
  }
}
