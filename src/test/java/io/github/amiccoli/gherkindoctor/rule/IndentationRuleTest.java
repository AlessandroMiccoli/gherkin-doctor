package io.github.amiccoli.gherkindoctor.rule;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import java.util.EnumMap;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.BACKGROUND;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE_DESCRIPTION;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static io.github.amiccoli.gherkindoctor.helper.FeatureReaderHelper.mockInvalidGherkinDocumentForBackgroundIndentationRule;
import static io.github.amiccoli.gherkindoctor.helper.FeatureReaderHelper.mockInvalidGherkinDocumentForFeatureIndentationRule;
import static io.github.amiccoli.gherkindoctor.helper.FeatureReaderHelper.mockInvalidGherkinDocumentForScenarioIndentationRule;
import static io.github.amiccoli.gherkindoctor.helper.FeatureReaderHelper.mockValidGherkinDocumentIndentationRule;
import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDoctorConfigurationHelper.mockGherkinDoctorConfiguration;
import static io.github.amiccoli.gherkindoctor.helper.MockRuleConstraintHelper.mockIndentationRuleConfiguration;
import static io.github.amiccoli.gherkindoctor.helper.RulesConfigurationHelper.mockRulesConfiguration;
import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class IndentationRuleTest {

    @Test
    void shouldSetIndentationConstraints() {
        // Given
        var mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        var rule = new IndentationRule();

        // When
        rule.setConstraint(mockGherkinDoctorConfig);
        var constraints = rule.getIndentationConstraints();

        // Then
        assertThat(constraints)
                .hasSize(2)
                .containsEntry(FEATURE, 1L)
                .containsEntry(GherkinElement.SCENARIO, 3L);
    }

    @Test
    void shouldNotReturnRuleErrorWhenIndentationConstraintsArePassed() {
        // Given
        var mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        var mockGherkinDocument = mockValidGherkinDocumentIndentationRule();
        var rule = new IndentationRule();
        rule.setConstraint(mockGherkinDoctorConfig);

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).isEmpty();
        verify((mockGherkinDocument.getFeature().get().getLocation())).getColumn();
        verify((mockGherkinDocument.getFeature().get().getChildren().get(0).getScenario().get().getLocation())).getColumn();
    }

    @Test
    void shouldReturnFeatureRuleErrorWhenIndentationConstraintFails() {
        // Given
        var mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        var mockRulesConfig = mockRulesConfiguration();
        given(mockGherkinDoctorConfig.getRules()).willReturn(mockRulesConfig);
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{ put(FEATURE, 1L); }}
        );
        given(mockRulesConfig.getIndentation()).willReturn(mockIndentationRule);

        var mockGherkinDocument = mockInvalidGherkinDocumentForFeatureIndentationRule();
        var rule = new IndentationRule();
        rule.setConstraint(mockGherkinDoctorConfig);

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).hasSize(1);

        var ruleError = ruleErrors.get(0);
        assertThat(ruleError.getUri()).isEqualTo("FakeIndentationRuleTest.feature");
        assertThat(ruleError.getKeyword()).isEqualTo(FEATURE);
        assertThat(ruleError.getType()).isEqualTo(INDENTATION);
        assertThat(ruleError.getActual()).isEqualTo("Actual feature indentation is 2.");
        assertThat(ruleError.getExpected()).isEqualTo("Expected feature indentation is 1.");
    }

    @Test
    void shouldReturnBackgroundRuleErrorWhenIndentationConstraintFails() {
        // Given
        var mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        var mockRulesConfig = mockRulesConfiguration();
        given(mockGherkinDoctorConfig.getRules()).willReturn(mockRulesConfig);
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{ put(BACKGROUND, 3L); }}
        );
        given(mockRulesConfig.getIndentation()).willReturn(mockIndentationRule);

        var mockGherkinDocument = mockInvalidGherkinDocumentForBackgroundIndentationRule();
        var rule = new IndentationRule();
        rule.setConstraint(mockGherkinDoctorConfig);

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).hasSize(1);

        var ruleError = ruleErrors.get(0);
        assertThat(ruleError.getUri()).isEqualTo("FakeIndentationRuleTest.feature");
        assertThat(ruleError.getKeyword()).isEqualTo(BACKGROUND);
        assertThat(ruleError.getType()).isEqualTo(INDENTATION);
        assertThat(ruleError.getActual()).isEqualTo("Actual background indentation is 5.");
        assertThat(ruleError.getExpected()).isEqualTo("Expected background indentation is 3.");
    }

    @Test
    void shouldReturnScenarioRuleErrorWhenIndentationConstraintFails() {
        // Given
        var mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        var mockRulesConfig = mockRulesConfiguration();
        given(mockGherkinDoctorConfig.getRules()).willReturn(mockRulesConfig);
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{ put(SCENARIO, 3L); }}
        );
        given(mockRulesConfig.getIndentation()).willReturn(mockIndentationRule);

        var mockGherkinDocument = mockInvalidGherkinDocumentForScenarioIndentationRule();
        var rule = new IndentationRule();
        rule.setConstraint(mockGherkinDoctorConfig);

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).hasSize(1);

        var ruleError = ruleErrors.get(0);
        assertThat(ruleError.getUri()).isEqualTo("FakeIndentationRuleTest.feature");
        assertThat(ruleError.getKeyword()).isEqualTo(SCENARIO);
        assertThat(ruleError.getType()).isEqualTo(INDENTATION);
        assertThat(ruleError.getActual()).isEqualTo("Actual scenario indentation is 6.");
        assertThat(ruleError.getExpected()).isEqualTo("Expected scenario indentation is 3.");
    }

    // TODO: this test should be parameterised including all the Gherkin elements without validation.
    @Test
    void shouldSkipValidationWhenIndentationConstraintIsNotHandled() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(IndentationRule.class);
        var mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        var mockRulesConfig = mockRulesConfiguration();
        given(mockGherkinDoctorConfig.getRules()).willReturn(mockRulesConfig);
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{ put(FEATURE_DESCRIPTION, 3L); }}
        );
        given(mockRulesConfig.getIndentation()).willReturn(mockIndentationRule);

        var mockGherkinDocument = mockValidGherkinDocumentIndentationRule();
        var rule = new IndentationRule();
        rule.setConstraint(mockGherkinDoctorConfig);

        // When
        var ruleErrors = rule.apply(mockGherkinDocument);

        // Then
        assertThat(ruleErrors).isEmpty();

        var logMessage = "Skip validation due to Gherkin element [FEATURE_DESCRIPTION] not currently handled for indentation.";
        LoggerTestHelper.verifyLog(listAppender, Level.WARN, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }
}
