package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;

import java.util.UUID;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.camel.Exchange;

public class InvoiceRoute {
  
  private static final Logger logger = LoggerFactory.getLogger(InvoiceRoute.class);
    
  private void sendFile(UUID id, String question) {
    try {
      
        CamelContext camel = new DefaultCamelContext();
        camel.start();
      
        Component component = camel.getComponent("file");
 
        Endpoint endpoint = component.createEndpoint("file://accounting/invoices");
 
        Exchange exchange = endpoint.createExchange();
        exchange.getIn().setBody(question);
       
        exchange.getIn().setHeader(FileComponent.FILE_EXCHANGE_FILE, "Invoice" + id + ".xml");
 
        Producer producer = endpoint.createProducer();
        producer.start();

        producer.process(exchange);
        producer.stop();
        
        logger.info("Invoice File created"); 
        
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

}
