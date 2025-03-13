package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GherkinElementTest {

    @ParameterizedTest
    @MethodSource("provideEnumAndMatcher")
    void shouldMatchGherkinElementEnumValueWhenUpperCase(GherkinElement enumParam, String element) {
        // Given
        // When
        // Then
        assertEquals(element.toUpperCase(), enumParam.name());
    }

    @ParameterizedTest
    @MethodSource("provideEnumAndMatcher")
    void shouldMatchGherkinElementEnumValue(GherkinElement enumParam, String alias) {
        // Given
        // When
        // Then
        assertEquals(alias, enumParam.getValue());
    }

    @Test
    void shouldMatchGherkinElementEnumLength() {
        // Given
        // When
        // Then
        assertEquals(6, GherkinElement.values().length);
    }

    @ParameterizedTest
    @MethodSource("provideEnumAndMatcher")
    void shouldReturnTheCorrectGherkinElementEnumWhenValueBelongFromTheAvailableValues(GherkinElement enumParam, String desiredValue) {
        // Given
        // When
        var element = GherkinElement.from(desiredValue);

        // Then
        assertEquals(enumParam, element);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDesiredValueDoesntBelongToTheGherkinElementValues() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> GherkinElement.from("anyInvalidValue"))
                .isInstanceOf(ConfigurationException.class)
                .hasMessage("Gherkin element not found for [anyInvalidValue].");
    }

    private static Stream<Arguments> provideEnumAndMatcher() {
        return Stream.of(
                arguments(FEATURE, "feature"),
                arguments(RULE, "rule"),
                arguments(BACKGROUND, "background"),
                arguments(SCENARIO, "scenario"),
                arguments(STEP, "step"),
                arguments(EXAMPLES, "examples")
        );
    }
}
