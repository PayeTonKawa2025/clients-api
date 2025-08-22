package fr.payetonkawa.clients.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "global.events";
    public static final String QUEUE_NAME = "service.client.queue";

    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue clientQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding clientBinding(Queue clientQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(clientQueue)
                .to(exchange)
                .with("#");
    }

}
