package io.github.amiccoli.gherkindoctor.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MissingGherkinElementExceptionTest {

    @Test
    void shouldConstructExceptionWithoutMessage() {
        // Given
        // When
        var error = new MissingGherkinElementException();

        // Then
        assertNull(error.getMessage());
    }

    @Test
    void shouldConstructExceptionWithMessage() {
        // Given
        var message = "Test message.";

        // When
        var error = new MissingGherkinElementException(message);

        // Then
        assertEquals("Test message.", error.getMessage());
    }


    @Test
    void shouldConstructorExceptionWithCause() {
        // Given
        var cause = new RuntimeException("Cause of the exception.");

        // When
        var error = new MissingGherkinElementException(cause);

        // Then
        assertEquals(cause, error.getCause());
    }

    @Test
    void shouldConstructExceptionWithMessageAndCause() {
        // Given
        var message = "Test message.";
        var cause = new RuntimeException("Cause of the exception.");

        // When
        var error = new MissingGherkinElementException(message, cause);

        // Then
        assertEquals("Test message.", error.getMessage());
        assertEquals(cause, error.getCause());
    }
}
