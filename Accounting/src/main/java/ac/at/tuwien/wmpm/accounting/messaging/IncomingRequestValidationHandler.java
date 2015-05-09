package ac.at.tuwien.wmpm.accounting.messaging;

import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
import ac.at.tuwien.wmpm.domain.model.IncomingRequest;
import ac.at.tuwien.wmpm.domain.model.User;
import ac.at.tuwien.wmpm.domain.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dietl_ma on 09/05/15.
 */
@Service
@Transactional("transactionManager")
public class IncomingRequestValidationHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void handleMessage(IncomingRequest ir) throws MessagingException {

        User u = userRepository.findByEmail(ir.getMail());

        if (u == null) {
            User newUser = new User();
            newUser.setEmail(ir.getMail());
            userRepository.save(newUser);
            u = newUser;
        }

        //do some validation stuff
        ir.setValid(true);

        rabbitTemplate.convertAndSend(CommonRabbitConfiguration.INCOMING_REQUEST_VALIDATION_RESPONSE, ir);
    }
}
