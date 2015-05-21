package ac.at.tuwien.wmpm.experts.configuration;

import ac.at.tuwien.wmpm.domain.configuration.CommonRabbitConfiguration;
import ac.at.tuwien.wmpm.experts.messaging.MessageHandler;

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

/**
 * Created by dietl_ma on 21/04/15.
 */
@Configuration
public class RabbitConfiguration {

    /** The application context. */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MessageHandler messageHandler;


    /**
     * The messageListenerContainer.
     * @param connectionFactory
     * @param amqpTemplate
     * @param messageConverter
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, RabbitTemplate amqpTemplate, MessageConverter messageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(messageConverter);
        container.setQueues((Queue)applicationContext.getBean(CommonRabbitConfiguration.TEST_QUEUE));

        MessageListenerAdapter adapter = new MessageListenerAdapter(messageHandler, messageConverter);
        adapter.setMessageConverter(messageConverter);
        container.setMessageListener(adapter);
        return container;
    }
}
