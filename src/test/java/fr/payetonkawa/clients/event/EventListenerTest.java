package fr.payetonkawa.clients.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventListenerTest {

    @InjectMocks
    private EventListener eventListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleEvent_ShouldProcessMessage_WhenValidJsonProvided() throws JsonProcessingException {
        // Arrange
        String jsonMessage = "{\"payload\":{\"id\":1,\"name\":\"Test\"}}";
        String routingKey = "client.created";

        Message amqpMessage = mock(Message.class);
        MessageProperties messageProperties = mock(MessageProperties.class);
        when(amqpMessage.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getReceivedRoutingKey()).thenReturn(routingKey);

        // Act & Assert
        assertDoesNotThrow(() -> eventListener.handleEvent(jsonMessage, amqpMessage));
        verify(amqpMessage, times(1)).getMessageProperties();
        verify(messageProperties, times(1)).getReceivedRoutingKey();
    }

    @Test
    void handleEvent_ShouldThrowJsonProcessingException_WhenInvalidJsonProvided() {
        // Arrange
        String invalidJsonMessage = "invalid json";
        String routingKey = "client.created";

        Message amqpMessage = mock(Message.class);
        MessageProperties messageProperties = mock(MessageProperties.class);
        when(amqpMessage.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getReceivedRoutingKey()).thenReturn(routingKey);

        // Act & Assert
        assertThrows(JsonProcessingException.class, () -> eventListener.handleEvent(invalidJsonMessage, amqpMessage));
    }

    @Test
    void handleEvent_ShouldProcessMessage_WhenPayloadIsNull() throws JsonProcessingException {
        // Arrange
        String jsonMessage = "{\"payload\":null}";
        String routingKey = "client.updated";

        Message amqpMessage = mock(Message.class);
        MessageProperties messageProperties = mock(MessageProperties.class);
        when(amqpMessage.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getReceivedRoutingKey()).thenReturn(routingKey);

        // Act & Assert
        assertDoesNotThrow(() -> eventListener.handleEvent(jsonMessage, amqpMessage));
        verify(amqpMessage, times(1)).getMessageProperties();
        verify(messageProperties, times(1)).getReceivedRoutingKey();
    }

    @Test
    void handleEvent_ShouldProcessMessage_WhenPayloadIsMissing() throws JsonProcessingException {
        // Arrange
        String jsonMessage = "{\"data\":\"test\"}";
        String routingKey = "client.deleted";

        Message amqpMessage = mock(Message.class);
        MessageProperties messageProperties = mock(MessageProperties.class);
        when(amqpMessage.getMessageProperties()).thenReturn(messageProperties);
        when(messageProperties.getReceivedRoutingKey()).thenReturn(routingKey);

        // Act & Assert
        assertDoesNotThrow(() -> eventListener.handleEvent(jsonMessage, amqpMessage));
        verify(amqpMessage, times(1)).getMessageProperties();
        verify(messageProperties, times(1)).getReceivedRoutingKey();
    }
}