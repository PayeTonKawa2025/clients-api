package fr.payetonkawa.clients.event;

import com.google.gson.Gson;
import fr.payetonkawa.clients.messaging.ExchangeMessage;
import fr.payetonkawa.clients.messaging.ExchangeQueues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventPublisher {

    private final AmqpTemplate amqpTemplate;

    private static final Gson gson = new Gson();

    public EventPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendEvent(String routingKey, ExchangeMessage message) {
        message.setExchangeId(ExchangeQueues.EXCHANGE_NAME);
        message.setRoutingKey(routingKey);
        message.setType(routingKey);
        amqpTemplate.convertAndSend(ExchangeQueues.EXCHANGE_NAME, routingKey, gson.toJson(message));
        log.info("📤 Publishing to exchange '{}'routingKey '{} payload '{}'",
                ExchangeQueues.EXCHANGE_NAME, routingKey, gson.toJson(message));
    }
}
