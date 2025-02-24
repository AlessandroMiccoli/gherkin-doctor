package io.github.amiccoli.gherkindoctor.core;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import io.github.amiccoli.gherkindoctor.reader.FeatureReader;
import io.github.amiccoli.gherkindoctor.rule.IndentationRule;
import io.github.amiccoli.gherkindoctor.rule.RuleError;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static io.github.amiccoli.gherkindoctor.helper.GherkinDocumentHelper.mockInvalidGherkinDocument;
import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDoctorConfigurationHelper.mockGherkinDoctorConfiguration;
import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GherkinDoctorToolkitTest {

    @Test
    void shouldLogWhenNoGherkinDocumentIsFound() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(GherkinDoctorToolkit.class);
        var mockRule = mock(IndentationRule.class);
        var mockReader = mock(FeatureReader.class);
        var toolkit = new GherkinDoctorToolkit(mockReader, List.of(mockRule));

        given(mockReader.read()).willReturn(new ArrayList<>());

        // When
        toolkit.lint();

        // Then
        var logMessage = "Gherkin documents not found. Skipping validation.";
        LoggerTestHelper.verifyLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldLintReturningRuleErrorWhenInvalidGherkinDocument() {
        // Given
        var mockRule = mock(IndentationRule.class);
        var mockReader = mock(FeatureReader.class);
        var mockGherkinDocument = mockInvalidGherkinDocument();
        var toolkit = new GherkinDoctorToolkit(mockReader, List.of(mockRule));

        var ruleError = RuleError.builder()
                .uri("anyUri")
                .type(INDENTATION)
                .keyword(FEATURE)
                .actual("anyActualError")
                .expected("anyExpectedError")
                .build();

        given(mockReader.read()).willReturn(List.of(mockGherkinDocument));
        given(mockRule.apply(mockGherkinDocument)).willReturn(List.of(ruleError));

        // When
        var mappedRuleErrors = toolkit.lint();

        // Then
        assertThat(mappedRuleErrors).hasSize(1);
        assertThat(mappedRuleErrors.get("anyUri")).hasSize(1);
    }

    @Test
    void shouldLintReturningRuleErrorGroupedByUriWhenMultipleInvalidGherkinDocument() {
        // Given
        var mockRule = mock(IndentationRule.class);
        var mockReader = mock(FeatureReader.class);
        var mockGherkinDocument = mockInvalidGherkinDocument();
        var toolkit = new GherkinDoctorToolkit(mockReader, List.of(mockRule));

        var ruleError = RuleError.builder()
                .uri("anyUri")
                .type(INDENTATION)
                .keyword(FEATURE)
                .actual("anyActualError")
                .expected("anyExpectedError")
                .build();

        var ruleError2 = RuleError.builder()
                .uri("anyUri2")
                .type(INDENTATION)
                .keyword(SCENARIO)
                .actual("anyActualError2")
                .expected("anyExpectedError2")
                .build();

        given(mockReader.read()).willReturn(List.of(mockGherkinDocument));
        given(mockRule.apply(mockGherkinDocument)).willReturn(List.of(ruleError, ruleError2));

        // When
        var mappedRuleErrors = toolkit.lint();

        // Then
        assertThat(mappedRuleErrors).hasSize(2);
        assertThat(mappedRuleErrors.get("anyUri")).hasSize(1);
        assertThat(mappedRuleErrors.get("anyUri2")).hasSize(1);
    }
}
