package fr.payetonkawa.clients.event;

import com.google.gson.Gson;
import fr.payetonkawa.clients.config.RabbitMQConfig;
import fr.payetonkawa.common.exchange.ExchangeQueues;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final AmqpTemplate amqpTemplate;

    private static final Gson gson = new Gson();

    public EventPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendEvent(String routingKey, Object payload) {
        amqpTemplate.convertAndSend(ExchangeQueues.EXCHANGE_NAME, routingKey, gson.toJson(payload));
    }
}
