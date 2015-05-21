package ac.at.tuwien.wmpm.accounting.configuration;

import ac.at.tuwien.wmpm.accounting.messaging.IncomingRequestValidationHandler;
import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHandler;

/**
 * Created by dietl_ma on 21/04/15.
 */
//@Configuration
public class RabbitConfiguration {

    /** The application context. */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IncomingRequestValidationHandler incomingRequestValidationHandler;


    /**
     * The messageListenerContainer.
     * @param connectionFactory
     * @param amqpTemplate
     * @param messageConverter
     * @return
     */
//    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, RabbitTemplate amqpTemplate, MessageConverter messageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(messageConverter);
        container.setQueues((Queue)applicationContext.getBean("incomingRequestValidationQueue"));

        MessageListenerAdapter adapter = new MessageListenerAdapter(incomingRequestValidationHandler, messageConverter);
        adapter.setMessageConverter(messageConverter);
        container.setMessageListener(adapter);
        return container;
    }
}
