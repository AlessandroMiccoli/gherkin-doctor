package io.github.amiccoli.gherkindoctor.rule;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import java.util.EnumMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.BACKGROUND;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static io.github.amiccoli.gherkindoctor.helper.GherkinDocumentHelper.mockInvalidGherkinDocument;
import static io.github.amiccoli.gherkindoctor.helper.GherkinDocumentHelper.mockValidGherkinDocument;
import static io.github.amiccoli.gherkindoctor.helper.MockRuleConstraintHelper.mockIndentationRuleConfiguration;
import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class IndentationRuleTest {

    @Test
    void shouldNotReturnRuleErrorWhenIndentationConstraintsArePassed() {
        // Given
        var mockIndentationRule = mockIndentationRuleConfiguration();
        var rule = new IndentationRule(mockIndentationRule.getMappings());
        var mockGherkinDocument = mockValidGherkinDocument();

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).isEmpty();
        verify((mockGherkinDocument.getFeature().get().getLocation())).getColumn();
        verify((mockGherkinDocument.getFeature().get().getChildren().get(0).getScenario().get().getLocation())).getColumn();
    }

    @ParameterizedTest
    @MethodSource("indentationFailureCases")
    void shouldReturnRuleErrorWhenIndentationConstraintFails(GherkinElement element, long expected, long actual) {
        // Given
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{ put(element, expected); }}
        );
        var rule = new IndentationRule(mockIndentationRule.getMappings());
        var mockGherkinDocument = mockInvalidGherkinDocument();

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).hasSize(1);

        var ruleError = ruleErrors.get(0);
        assertThat(ruleError.getUri()).isEqualTo("FakeIndentationRuleTest.feature");
        assertThat(ruleError.getKeyword()).isEqualTo(element);
        assertThat(ruleError.getType()).isEqualTo(INDENTATION);
        assertThat(ruleError.getActual())
                .isEqualTo("Actual %s indentation is %d.".formatted(element.getValue(), actual));
        assertThat(ruleError.getExpected())
                .isEqualTo("Expected %s indentation is %d.".formatted(element.getValue(), expected));
    }

    @ParameterizedTest
    @EnumSource(value = GherkinElement.class, mode = EnumSource.Mode.EXCLUDE, names = {"FEATURE", "BACKGROUND", "SCENARIO"})
    void shouldSkipValidationWhenIndentationConstraintIsNotHandled(GherkinElement element) {
        // Given
        var listAppender = LoggerTestHelper.startLogger(IndentationRule.class);
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{ put(element, 3L); }}
        );
        var rule = new IndentationRule(mockIndentationRule.getMappings());
        var mockGherkinDocument = mockValidGherkinDocument();

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).isEmpty();

        var logMessage = "Skip validation due to Gherkin element [%s] not currently handled for indentation."
                .formatted(element);
        LoggerTestHelper.verifyLog(listAppender, Level.WARN, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    static Stream<Arguments> indentationFailureCases() {
        return Stream.of(
                Arguments.of(FEATURE, 1L, 2L),
                Arguments.of(BACKGROUND, 3L, 5L),
                Arguments.of(SCENARIO, 3L, 6L)
        );
    }
}
