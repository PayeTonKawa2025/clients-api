package fr.payetonkawa.clients.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static fr.payetonkawa.clients.messaging.ExchangeQueues.CLIENT_QUEUE_NAME;

@Component
@Log4j2
public class EventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = CLIENT_QUEUE_NAME)
    public void handleEvent(String message, Message amqpMessage) throws JsonProcessingException {
        String routingKey = amqpMessage.getMessageProperties().getReceivedRoutingKey();
        log.info("📩 Received event with routing key: {}", routingKey);

        JsonNode root = objectMapper.readTree(message);
        JsonNode payloadNode = root.get("payload");
    }

}
