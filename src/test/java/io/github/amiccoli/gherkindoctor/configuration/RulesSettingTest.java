package io.github.amiccoli.gherkindoctor.configuration;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import java.util.EnumMap;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.MockRuleSettingHelper.mockIndentationRuleSetting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class RulesSettingTest {

    @Test
    void shouldHandleIllegalArgumentExceptionWhenIndentationRuleIsActiveButHasNoMappings() {
        // Given
        var mockIndentationRuleSetting = mockIndentationRuleSetting();
        given(mockIndentationRuleSetting.getMappings()).willReturn(new EnumMap<>(GherkinElement.class));
        var setting = new RulesSetting(mockIndentationRuleSetting);

        // When
        // Then
        assertThatThrownBy(setting::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Indentation rule must have at least one mapping when is active.");
    }

    @Test
    void shouldSkipValidationWhenIndentationRuleIsInactive() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(RulesSetting.class);
        var mockIndentationRuleSetting = mockIndentationRuleSetting();
        given(mockIndentationRuleSetting.isActive()).willReturn(false);
        var setting = new RulesSetting(mockIndentationRuleSetting);

        // When
        // Then
        assertThatCode(setting::validate)
                .doesNotThrowAnyException();

        var logMessage = "Indentation rule is inactive. Skipping validation of configured properties.";
        LoggerTestHelper.verifyLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldPassValidationWhenIndentationRuleIsConfigured() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(RulesSetting.class);
        var mockIndentationRuleSetting = mockIndentationRuleSetting();

        // When
        var setting = new RulesSetting(mockIndentationRuleSetting);

        // Then
        assertThatCode(setting::validate)
                .doesNotThrowAnyException();

        assertThat(listAppender.list).isEmpty();
        LoggerTestHelper.stopLogger(listAppender);
    }
}
