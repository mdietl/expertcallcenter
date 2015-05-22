package ac.at.tuwien.wmpm.coordinator.processors;

import ac.at.tuwien.wmpm.domain.model.Category;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import mx.bigdata.jcalais.CalaisClient;
import mx.bigdata.jcalais.CalaisObject;
import mx.bigdata.jcalais.CalaisResponse;
import mx.bigdata.jcalais.rest.CalaisRestClient;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Component
public class EnrichWithCategoriesProcessor implements Processor {

  @Value("${opencalais.apiKey}")
  private String apiKey;

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(EnrichWithCategoriesProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {

    // enrich message with tags
    IncomingRequest ir = (IncomingRequest) exchange.getIn().getBody();

    CalaisClient calaisClient = new CalaisRestClient(apiKey);
    CalaisResponse calaisResponse = calaisClient.analyze(ir.getQuestion());

    for (CalaisObject tag : calaisResponse.getSocialTags()) {
      logger.info(tag.getField("_typeGroup") + ":" + tag.getField("name"));

      ir.getCategories().add(new Category(tag.getField("name")));
    }

    logger.info("processed mail and found tags: " + ir.getCategories());
  }
}
