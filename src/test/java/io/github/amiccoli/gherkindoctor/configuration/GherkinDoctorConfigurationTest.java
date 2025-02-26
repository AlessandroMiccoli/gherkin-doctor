package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import io.github.amiccoli.gherkindoctor.helper.AbstractSpringTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.amiccoli.gherkindoctor.helper.RulesConfigurationHelper.mockRulesConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class GherkinDoctorConfigurationTest {

    @Nested
    @SpringBootTest(properties = "spring.config.name=application-test-active-rules")
    class TestGherkinDoctorConfigurationTest extends AbstractSpringTest {

        @Autowired
        private GherkinDoctorConfiguration config;

        @Test
        void shouldHaveFeatureLocation() {
            // Given
            // When
            // Then
            assertThat(config.getFeatureLocation()).isEqualTo("features");
        }

        @Test
        void shouldHaveRules() {
            // Given
            // When
            // Then
            assertThat(config.getRules()).isNotNull();
            assertThat(config.getRules().getIndentation().isActive()).isTrue();
            assertThat(config.getRules().getIndentation().getMappings())
                    .hasSize(2)
                    .containsEntry(GherkinElement.FEATURE, 0L)
                    .containsEntry(GherkinElement.SCENARIO, 2L);
        }
    }

    @Test
    void shouldPassPostConstructValidationWhenValuesAreCorrect() {
        // Given
        var mockRulesConfig = mockRulesConfiguration();
        var config = new GherkinDoctorConfiguration("anyFeatureLocation", mockRulesConfig);

        // When
        assertThatCode(config::postConstruct)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowConfigurationExceptionWhenFeatureLocationIsNull() {
        // Given
        var mockRulesConfig = mockRulesConfiguration();
        var config = new GherkinDoctorConfiguration(null, mockRulesConfig);

        // When
        assertThatThrownBy(config::postConstruct)
                .isInstanceOf(ConfigurationException.class)
                .hasMessage("Feature resource location is a mandatory property.");
    }

    @Test
    void shouldValidateRulesConfigurationWhenPostConstructIsCalled() {
        // Given
        var mockRulesConfig = mockRulesConfiguration();
        var config = new GherkinDoctorConfiguration("anyFeatureLocation", mockRulesConfig);

        // When
        config.postConstruct();

        // Then
        verify(mockRulesConfig).validate();
    }

    @Test
    void shouldThrowConfigurationExceptionWhenValidateRulesThrowIllegalArgumentException() {
        // Given
        var mockRulesConfig = mockRulesConfiguration();
        var config = new GherkinDoctorConfiguration("anyFeatureLocation", mockRulesConfig);

        doThrow(new IllegalArgumentException("any error")).when(mockRulesConfig).validate();

        // When
        // Then
        assertThatThrownBy(config::postConstruct)
                .isInstanceOf(ConfigurationException.class)
                .hasMessage("any error");
    }
}
