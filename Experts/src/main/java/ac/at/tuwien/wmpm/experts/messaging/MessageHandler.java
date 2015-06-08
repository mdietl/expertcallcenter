package ac.at.tuwien.wmpm.experts.messaging;

import java.util.List;
import java.util.ArrayList;

import ac.at.tuwien.wmpm.domain.repository.CategoryRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;
  
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        IncomingRequest incomingRequest = exchange.getIn().getBody(IncomingRequest.class);
        logger.info("Request got by experts: " + incomingRequest);

        logger.info("Find experts for the categories: {" + incomingRequest.getCategories() + "}");



        List<Category> categories = new ArrayList<>();
        for (String catString : incomingRequest.getCategories()) {
            Category cat =  categoryRepository.findByName(catString);

            if (cat != null) {
                categories.add(cat);
            }
        }
        for(Expert expert : expertRepository.findByCategoriesIn(categories)) {
            incomingRequest.getExperts().add(expert.getEmail());
            logger.info("\tExpert '" + expert.getEmail() + "' add for the category!");
        }



    }
}
