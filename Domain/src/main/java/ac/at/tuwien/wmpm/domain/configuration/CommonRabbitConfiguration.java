package ac.at.tuwien.wmpm.domain.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

/**
 * Created by dietl_ma on 21/04/15.
 */
public class CommonRabbitConfiguration {

    /** The Constant TOPIC_EXCHANGE. */
    public static final String TOPIC_EXCHANGE =  "expertCallCenterExchange";

    /** The Constant TEST_QUEUE. */
    public static final String TEST_QUEUE =  "testQueue";

    public static final String INCOMING_REQUEST_VALIDATION = "incomingRequestValidation";

    public static final String INCOMING_REQUEST_VALIDATION_RESPONSE = "incomingRequestValidationResponse";


    /** The uuid. */
    public static UUID uuid;

    /** The connection factory. */
    @Autowired
    ConnectionFactory connectionFactory;

    /** The rabbit admin. */
    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * Topic exchange.
     *
     * @return the topic exchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    /**
     * Rabbit transaction manager.
     *
     * @param connectionFactory the connection factory
     * @return the rabbit transaction manager
     */
   /* @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }*/

    /**
     * Rabbit listener container factory.
     *
     * @param connectionFactory the connection factory
     * @return the simple rabbit listener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    /**
     * Amqp template.
     *
     * @param connectionFactory the connection factory
     * @return the rabbit template
     */
    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setReplyTimeout(10000);
        return template;

    }

    /**
     * Json message converter.
     *
     * @return the message converter
     */
    @Bean
    public MessageConverter jsonMessageConverter(){
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.enableDefaultTyping();
        jsonMessageConverter.setJsonObjectMapper(jsonObjectMapper);
        return jsonMessageConverter;
    }

    /**
     * The test queue.
     * @return
     */
    @Bean
    public Queue testQueue() {
        Queue queue = new Queue(TEST_QUEUE);
        return queue;
    }

    /**
     * Test queue binding.
     *
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding testQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(testQueue()).to(exchange).with(testQueue().getName());
    }


    @Bean
    public Queue incomingRequestValidationQueue() {
        Queue queue = new Queue(INCOMING_REQUEST_VALIDATION);
        return queue;
    }

    @Bean
    public Binding incomingRequestValidtionQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(incomingRequestValidationQueue()).to(exchange).with(incomingRequestValidationQueue().getName());
    }

    @Bean
    public Queue incomingRequestValidtionResponseQueue() {
        Queue queue = new Queue(INCOMING_REQUEST_VALIDATION_RESPONSE);
        return queue;
    }

    @Bean
    public Binding incomingRequestValidtionResponseQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(incomingRequestValidtionResponseQueue()).to(exchange).with(incomingRequestValidtionResponseQueue().getName());
    }

    /**
     * Rabbit admin.
     *
     * @param connectionFactory the connection factory
     * @return the rabbit admin
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Uuid.
     *
     * @return the string
     */
    @Bean
    public String uuid() {
        uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
