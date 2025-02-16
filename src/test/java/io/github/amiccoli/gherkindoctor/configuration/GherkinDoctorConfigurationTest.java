package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import io.github.amiccoli.gherkindoctor.helper.AbstractSpringTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GherkinDoctorConfigurationTest {

    @Nested
    @ActiveProfiles("test-config-valid")
    class TestGherkinDoctorConfigurationTest extends AbstractSpringTest {

        @Autowired
        private GherkinDoctorConfiguration config;

        @Test
        void shouldHaveDriver() {
            // Given
            // When
            // Then
            assertThat(config.getFeatureLocation()).isEqualTo("features");
        }
    }

    @Test
    void shouldPassPostConstructValidationWhenValuesAreCorrect() {
        // Given
        var config = new GherkinDoctorConfiguration("anyFeatureLocation");

        // When
        assertThatCode(config::postConstruct)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldHandleConfigurationExceptionWhenFeatureLocationIsNull() {
        // Given
        var config = new GherkinDoctorConfiguration(null);

        // When
        assertThatThrownBy(config::postConstruct)
                .isInstanceOf(ConfigurationException.class)
                .hasMessage("Feature resource location is a mandatory property.");
    }
}
