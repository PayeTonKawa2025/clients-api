package fr.payetonkawa.clients.event;

import fr.payetonkawa.clients.messaging.ExchangeMessage;
import fr.payetonkawa.clients.messaging.ExchangeQueues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventPublisherTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private EventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEvent_ShouldPublishMessage_WhenValidParametersProvided() {
        // Arrange
        String routingKey = "client.created";
        ExchangeMessage message = new ExchangeMessage();
        message.setPayload("Test payload");

        // Act
        eventPublisher.sendEvent(routingKey, message);

        // Assert
        assertEquals(ExchangeQueues.EXCHANGE_NAME, message.getExchangeId());
        assertEquals(routingKey, message.getRoutingKey());
        assertEquals(routingKey, message.getType());

        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(amqpTemplate, times(1)).convertAndSend(
                exchangeCaptor.capture(),
                routingKeyCaptor.capture(),
                messageCaptor.capture()
        );

        assertEquals(ExchangeQueues.EXCHANGE_NAME, exchangeCaptor.getValue());
        assertEquals(routingKey, routingKeyCaptor.getValue());
        assertTrue(messageCaptor.getValue().contains("Test payload"));
    }

    @Test
    void sendEvent_ShouldSetMessageProperties_WhenCalled() {
        // Arrange
        String routingKey = "client.updated";
        ExchangeMessage message = new ExchangeMessage();
        message.setPayload("Update payload");

        // Act
        eventPublisher.sendEvent(routingKey, message);

        // Assert
        assertEquals(ExchangeQueues.EXCHANGE_NAME, message.getExchangeId());
        assertEquals(routingKey, message.getRoutingKey());
        assertEquals(routingKey, message.getType());
        verify(amqpTemplate, times(1)).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    void sendEvent_ShouldHandleNullPayload_WhenMessageHasNullPayload() {
        // Arrange
        String routingKey = "client.deleted";
        ExchangeMessage message = new ExchangeMessage();
        message.setPayload(null);

        // Act
        eventPublisher.sendEvent(routingKey, message);

        // Assert
        assertEquals(ExchangeQueues.EXCHANGE_NAME, message.getExchangeId());
        assertEquals(routingKey, message.getRoutingKey());
        assertEquals(routingKey, message.getType());
        verify(amqpTemplate, times(1)).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    void sendEvent_ShouldUseCorrectExchange_WhenCalled() {
        // Arrange
        String routingKey = "test.routing.key";
        ExchangeMessage message = new ExchangeMessage();

        // Act
        eventPublisher.sendEvent(routingKey, message);

        // Assert
        verify(amqpTemplate, times(1)).convertAndSend(
                eq(ExchangeQueues.EXCHANGE_NAME),
                eq(routingKey),
                anyString()
        );
    }
}