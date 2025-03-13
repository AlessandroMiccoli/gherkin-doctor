package io.github.amiccoli.gherkindoctor.configuration;

import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.MockRulesSettingHelper.mockRulesSetting;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class DoctorSettingTest {

    @Test
    void shouldPassPostConstructValidationWhenValuesAreCorrect() {
        // Given
        var mockRulesConfig = mockRulesSetting();
        var doctorSetting = new DoctorSetting("anyFeatureLocation", mockRulesConfig);

        // When
        assertThatCode(doctorSetting::validate)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowConfigurationExceptionWhenFeatureLocationIsNull() {
        // Given
        var mockRulesSetting = mockRulesSetting();
        var doctorSetting = new DoctorSetting(null, mockRulesSetting);

        // When
        assertThatThrownBy(doctorSetting::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Feature resource location is a mandatory property.");
    }

    @Test
    void shouldValidateRulesConfigurationWhenPostConstructIsCalled() {
        // Given
        var mockRulesSetting = mockRulesSetting();
        var doctorSetting = new DoctorSetting("anyFeatureLocation", mockRulesSetting);

        // When
        doctorSetting.validate();

        // Then
        verify(mockRulesSetting).validate();
    }

    @Test
    void shouldThrowConfigurationExceptionWhenValidateRulesThrowIllegalArgumentException() {
        // Given
        var mockRulesSetting = mockRulesSetting();
        var doctorSetting = new DoctorSetting("anyFeatureLocation", mockRulesSetting);

        doThrow(new IllegalArgumentException("any error")).when(mockRulesSetting).validate();

        // When
        // Then
        assertThatThrownBy(doctorSetting::validate)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("any error");
    }
}
