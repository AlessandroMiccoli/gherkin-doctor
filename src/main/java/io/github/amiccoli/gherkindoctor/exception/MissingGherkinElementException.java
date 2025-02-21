package io.github.amiccoli.gherkindoctor.exception;

import lombok.experimental.StandardException;

/**
 * Exception thrown when is missing an expected Gherkin element.
 */
@StandardException
public class MissingGherkinElementException extends RuntimeException { }
