package io.github.amiccoli.gherkindoctor.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfigurationExceptionTest {

    @Test
    void shouldConstructExceptionWithoutMessage() {
        // Given
        // When
        var error = new ConfigurationException();

        // Then
        assertNull(error.getMessage());
    }

    @Test
    void shouldConstructExceptionWithMessage() {
        // Given
        var message = "Test message.";

        // When
        var error = new ConfigurationException(message);

        // Then
        assertEquals("Test message.", error.getMessage());
    }


    @Test
    void shouldConstructorExceptionWithCause() {
        // Given
        var cause = new RuntimeException("Cause of the exception.");

        // When
        var error = new ConfigurationException(cause);

        // Then
        assertEquals(cause, error.getCause());
    }

    @Test
    void shouldConstructExceptionWithMessageAndCause() {
        // Given
        var message = "Test message.";
        var cause = new RuntimeException("Cause of the exception.");

        // When
        var error = new ConfigurationException(message, cause);

        // Then
        assertEquals("Test message.", error.getMessage());
        assertEquals(cause, error.getCause());
    }
}
