package io.github.amiccoli.gherkindoctor.exception;

import lombok.experimental.StandardException;

/**
 * Exception thrown when there is a misconfiguration in the application.
 */
@StandardException
public class ConfigurationException extends RuntimeException { }
