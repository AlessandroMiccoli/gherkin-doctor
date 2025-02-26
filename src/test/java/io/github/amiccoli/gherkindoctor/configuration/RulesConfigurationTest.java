package io.github.amiccoli.gherkindoctor.configuration;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import java.util.EnumMap;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.MockRuleConstraintHelper.mockIndentationRuleConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class RulesConfigurationTest {

    @Test
    void shouldHandleIllegalArgumentExceptionWhenIndentationRuleIsActiveButHasNoMappings() {
        // Given
        var mockIndentationRuleConfig = mockIndentationRuleConfiguration();
        given(mockIndentationRuleConfig.getMappings()).willReturn(new EnumMap<>(GherkinElement.class));
        var config = new RulesConfiguration(mockIndentationRuleConfig);

        // When
        // Then
        assertThatThrownBy(config::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Indentation rule must have at least one mapping when is active.");
    }

    @Test
    void shouldSkipValidationWhenIndentationRuleIsInactive() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(RulesConfiguration.class);
        var mockIndentationRuleConfig = mockIndentationRuleConfiguration();
        given(mockIndentationRuleConfig.isActive()).willReturn(false);
        var config = new RulesConfiguration(mockIndentationRuleConfig);

        // When
        // Then
        assertThatCode(config::validate)
                .doesNotThrowAnyException();

        var logMessage = "Indentation rule is inactive. Skipping validation of configured properties.";
        LoggerTestHelper.verifyLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldPassValidationWhenIndentationRuleIsConfigured() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(RulesConfiguration.class);
        var mockIndentationRuleConfig = mockIndentationRuleConfiguration();

        // When
        var config = new RulesConfiguration(mockIndentationRuleConfig);

        // Then
        assertThatCode(config::validate)
                .doesNotThrowAnyException();

        assertThat(listAppender.list).isEmpty();
        LoggerTestHelper.stopLogger(listAppender);
    }
}
