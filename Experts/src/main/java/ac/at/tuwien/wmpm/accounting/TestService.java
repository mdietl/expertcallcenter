package ac.at.tuwien.wmpm.accounting;

import ac.at.tuwien.wmpm.domain.model.Expert;
import ac.at.tuwien.wmpm.domain.repository.ExpertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by dietl_ma on 21/04/15.
 */
@Service
public class TestService {

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @PostConstruct
    private void onPostConstruct() {

        logger.info("TestService");
        for (Expert e : expertRepository.findAll()) {
            logger.info(e.getEmail());
        }





    }
}
