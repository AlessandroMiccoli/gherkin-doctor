package io.github.amiccoli.gherkindoctor.helper;

/*
 * Copyright (c) 2025 Alessandro Miccoli
 *
 * gherkin-doctor - A toolkit for analyzing and improving feature files written in Gherkin syntax.
 * Licensed under the Apache License 2.0.
 */

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import static org.assertj.core.api.Assertions.assertThat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnotationTestHelper {

    @SneakyThrows
    public static <T extends Annotation> T getAnnotationForMethod(
            Class<T> annotationType,
            String methodName,
            Class<?>[] parameters,
            Class<?> clazz
    ) {
        Optional<Annotation> maybeAnnotation = Arrays.stream(clazz.getMethod(methodName, parameters).getAnnotations())
                .filter(annotationType::isInstance)
                .findFirst();

        assertThat(maybeAnnotation).isPresent();

        //noinspection unchecked
        return (T) maybeAnnotation.get();
    }

    @SneakyThrows
    public static <T extends Annotation> T getAnnotationForMethod(
            Class<T> annotationType,
            String methodName,
            Class<?> clazz
    ) {
        Optional<Annotation> maybeAnnotation = Arrays.stream(clazz.getMethod(methodName).getAnnotations())
                .filter(annotationType::isInstance)
                .findFirst();

        assertThat(maybeAnnotation).isPresent();

        //noinspection unchecked
        return (T) maybeAnnotation.get();
    }

    @SneakyThrows
    public static <T extends Annotation> T getAnnotationForClass(
            Class<T> annotationType,
            Class<?> clazz
    ) {
        Optional<Annotation> maybeAnnotation = Arrays.stream(clazz.getAnnotations())
                .filter(annotationType::isInstance)
                .findFirst();

        assertThat(maybeAnnotation).isPresent();

        //noinspection unchecked
        return (T) maybeAnnotation.get();
    }
}
