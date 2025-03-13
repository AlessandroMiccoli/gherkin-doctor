package io.github.amiccoli.gherkindoctor.rule;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RuleStrategyTypeTest {

    @ParameterizedTest
    @MethodSource("provideEnumAndMatcher")
    void shouldMatchRuleTypeEnumValueWhenUpperCase(RuleType enumParam, String type) {
        // Given
        // When
        // Then
        assertEquals(type.toUpperCase(), enumParam.name());
    }

    @Test
    void shouldMatchRuleTypeEnumLength() {
        // Given
        // When
        // Then
        assertEquals(1, RuleType.values().length);
    }

    private static Stream<Arguments> provideEnumAndMatcher() {
        return Stream.of(
                arguments(INDENTATION, "indentation")
        );
    }
}
