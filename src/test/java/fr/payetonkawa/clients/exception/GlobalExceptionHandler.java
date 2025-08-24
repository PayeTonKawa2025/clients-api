package fr.payetonkawa.clients.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleIllegalArgumentException_ShouldReturnBadRequest_WhenIllegalArgumentExceptionThrown() {
        // Arrange
        String errorMessage = "Invalid argument provided";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get("status"));
        assertEquals("Bad request", body.get("error"));
        assertEquals(errorMessage, body.get("message"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleMissingDataException_ShouldReturnNotFound_WhenMissingDataExceptionThrown() {
        // Arrange
        String errorMessage = "Data not found";
        MissingDataException exception = new MissingDataException(errorMessage);

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleMissingDataException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals(errorMessage, body.get("message"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError_WhenGenericExceptionThrown() {
        // Arrange
        String errorMessage = "Something went wrong";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGenericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("Une erreur interne s'est produite", body.get("message"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleIllegalArgumentException_ShouldIncludeTimestamp_WhenCalled() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Test message");
        LocalDateTime beforeCall = LocalDateTime.now().minusSeconds(1);

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleIllegalArgumentException(exception);
        LocalDateTime afterCall = LocalDateTime.now().plusSeconds(1);

        // Assert
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        LocalDateTime timestamp = (LocalDateTime) body.get("timestamp");
        assertTrue(timestamp.isAfter(beforeCall));
        assertTrue(timestamp.isBefore(afterCall));
    }

    @Test
    void handleMissingDataException_ShouldIncludeTimestamp_WhenCalled() {
        // Arrange
        MissingDataException exception = new MissingDataException("Test message");
        LocalDateTime beforeCall = LocalDateTime.now().minusSeconds(1);

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleMissingDataException(exception);
        LocalDateTime afterCall = LocalDateTime.now().plusSeconds(1);

        // Assert
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        LocalDateTime timestamp = (LocalDateTime) body.get("timestamp");
        assertTrue(timestamp.isAfter(beforeCall));
        assertTrue(timestamp.isBefore(afterCall));
    }

    @Test
    void handleGenericException_ShouldIncludeTimestamp_WhenCalled() {
        // Arrange
        Exception exception = new Exception("Test message");
        LocalDateTime beforeCall = LocalDateTime.now().minusSeconds(1);

        // Act
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGenericException(exception);
        LocalDateTime afterCall = LocalDateTime.now().plusSeconds(1);

        // Assert
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        LocalDateTime timestamp = (LocalDateTime) body.get("timestamp");
        assertTrue(timestamp.isAfter(beforeCall));
        assertTrue(timestamp.isBefore(afterCall));
    }
}