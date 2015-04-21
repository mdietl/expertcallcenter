package ac.at.tuwien.wmpm.monitoring;

import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
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
    private RabbitTemplate rabbitTemplate;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @PostConstruct
    private void onPostConstruct() {

        //send message
        rabbitTemplate.convertAndSend(CommonRabbitConfiguration.TEST_QUEUE, "TEST NEW MESSAGE 123");
    }
}
