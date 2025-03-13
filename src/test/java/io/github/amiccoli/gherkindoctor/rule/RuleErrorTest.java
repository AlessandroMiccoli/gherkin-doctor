package io.github.amiccoli.gherkindoctor.rule;

import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;
import static org.assertj.core.api.Assertions.assertThat;

class RuleErrorTest {

    @Test
    void shouldConstructWithNoArgsConstructor() {
        // Given
        // When
        var error = new RuleError();

        // Then
        assertThat(error.getUri()).isBlank();
        assertThat(error.getType()).isNull();
        assertThat(error.getKeyword()).isNull();
        assertThat(error.getLine()).isNull();
        assertThat(error.getActual()).isNull();
        assertThat(error.getExpected()).isNull();

    }

    @Test
    void shouldConstructWithAllArgsConstructor() {
        // Given
        // When
        var error = new RuleError("anyUri", INDENTATION, FEATURE,
                2L, "anyActual", "anyExpected"
        );

        // Then
        assertThat(error.getUri()).isEqualTo("anyUri");
        assertThat(error.getType()).isEqualTo(INDENTATION);
        assertThat(error.getKeyword()).isEqualTo(FEATURE);
        assertThat(error.getLine()).isEqualTo(2L);
        assertThat(error.getActual()).isEqualTo("anyActual");
        assertThat(error.getExpected()).isEqualTo("anyExpected");
    }

    @Test
    void shouldCreateRuleErrorForIndentation() {
        // Given
        // When
        var error = RuleError.createForIndentationRule("anyUri", SCENARIO, 4L, 6L, 2L);

        // Then
        assertThat(error.getUri()).isEqualTo("anyUri");
        assertThat(error.getType()).isEqualTo(INDENTATION);
        assertThat(error.getKeyword()).isEqualTo(SCENARIO);
        assertThat(error.getLine()).isEqualTo(4L);
        assertThat(error.getActual()).isEqualTo("Actual scenario indentation is 6.");
        assertThat(error.getExpected()).isEqualTo("Expected scenario indentation is 2.");
    }
}
