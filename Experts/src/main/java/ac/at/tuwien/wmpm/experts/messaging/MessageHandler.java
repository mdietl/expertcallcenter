package ac.at.tuwien.wmpm.experts.messaging;

import java.util.List;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.model.Category;
import ac.at.tuwien.wmpm.domain.repository.ExpertRepository;

/**
 * Created by sadrian on 22/05/15.
 */
@Service
public class MessageHandler implements Processor {

  @Autowired
  private ExpertRepository expertRepository;
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    IncomingRequest incomingRequest = exchange.getIn().getBody(IncomingRequest.class);
    logger.info("Request got by experts: " + incomingRequest);

    logger.info("Find experts for the categories: {" + incomingRequest.getCategories() + "}");
    
    List<Expert> experts = new ArrayList<Expert>();
    
    for (Category category : incomingRequest.getCategories()) {
      logger.info("Searching experts for category '" + category.getName() + "'...");
      for(Expert expert : expertRepository.findByCategory(category)) {
        experts.add(expert);
        logger.info("\tExpert '" + expert.getEmail() + "' add for the category!");
      }
    }
  }
}
