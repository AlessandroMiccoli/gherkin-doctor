package io.github.amiccoli.gherkindoctor.exception;

import lombok.experimental.StandardException;

/**
 * Exception thrown when an issue occurs while accessing or reading a file.
 */
@StandardException
public class InvalidFileException extends RuntimeException { }
