package io.github.amiccoli.gherkindoctor.configuration;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import io.github.amiccoli.gherkindoctor.util.FileUtil;
import java.util.EnumMap;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DoctorConfigurationTest {

    @Test
    void shouldLoadDoctorConfigurationWhenSettingsAreCorrect() {
        try (val fileUtilMock = mockStatic(FileUtil.class)) {
            // Given
            val mockGherkinDoctorFile = DoctorConfigurationTest.class.getClassLoader().getResourceAsStream("gherkin-doctor-valid.yaml");
            fileUtilMock.when(() -> FileUtil.getInputStream(anyString())).thenReturn(mockGherkinDoctorFile);

            var mappings = new EnumMap<GherkinElement, Long>(GherkinElement.class) {{ put(FEATURE, 0L); put(SCENARIO, 2L); }};
            val expectedRulesSetting = new RulesSetting(new BaseRuleSetting<>(true, mappings));

            // When
            val doctorSetting = DoctorConfiguration.loadDoctorSetting();

            // Then
            assertNotNull(doctorSetting);
            assertThat(doctorSetting.getFeatureLocation()).isEqualTo("fake-feature-location");
            assertThat(doctorSetting.getRulesSetting())
                    .isNotNull()
                    .isEqualTo(expectedRulesSetting);
        }
    }

    @Test
    void shouldThrowConfigurationExceptionWhenGherkinDoctorFileHasInvalidFormat() {
        try (val fileUtilMock = mockStatic(FileUtil.class)) {
            // Given
            val mockGherkinDoctorFile = DoctorConfigurationTest.class.getClassLoader().getResourceAsStream("gherkin-doctor-malformed.yaml");
            fileUtilMock.when(() -> FileUtil.getInputStream(anyString())).thenReturn(mockGherkinDoctorFile);

            // When
            // Then
            assertThatThrownBy(DoctorConfiguration::loadDoctorSetting)
                    .isInstanceOf(ConfigurationException.class)
                    .hasMessageStartingWith("Invalid YAML format in [gherkin-doctor.yaml]: Unrecognized field \"invalid-rule\"");
        }
    }

    @Test
    void shouldThrowConfigurationExceptionWhenGherkinDoctorFileIsMissing() {
        try (val fileUtilMock = mockStatic(FileUtil.class)) {
            // Given
            val mockGherkinDoctorFile = DoctorConfigurationTest.class.getClassLoader().getResourceAsStream("gherkin-doctor-missing.yaml");
            fileUtilMock.when(() -> FileUtil.getInputStream(anyString())).thenReturn(mockGherkinDoctorFile);

            // When
            // Then
            assertThatThrownBy(DoctorConfiguration::loadDoctorSetting)
                    .isInstanceOf(ConfigurationException.class)
                    .hasMessage("Invalid file: argument \"src\" is null.");
        }
    }

    @Test
    void shouldThrowConfigurationExceptionWhenIOExceptionIsCaught() {
        try (val fileUtilMock = mockStatic(FileUtil.class)) {
            // Given
            fileUtilMock.when(() -> FileUtil.getInputStream(anyString()))
                    .thenThrow(new IOException("File read error"));

            // When
            // Then
            assertThatThrownBy(DoctorConfiguration::loadDoctorSetting)
                    .isInstanceOf(ConfigurationException.class)
                    .hasMessage("Error reading YAML configuration from [gherkin-doctor.yaml]: File read error.");
        }
    }

    @Test
    void shouldThrowConfigurationExceptionWhenGherkinDoctorFileHasInvalidProperties() {
        try (val fileUtilMock = mockStatic(FileUtil.class)) {
            // Given
            val mockGherkinDoctorFile = DoctorConfigurationTest.class.getClassLoader().getResourceAsStream("gherkin-doctor-invalid.yaml");
            fileUtilMock.when(() -> FileUtil.getInputStream(anyString())).thenReturn(mockGherkinDoctorFile);

            // When
            // Then
            assertThatThrownBy(DoctorConfiguration::loadDoctorSetting)
                    .isInstanceOf(ConfigurationException.class)
                    .hasMessageStartingWith("Invalid file: Feature resource location is a mandatory property.");
        }
    }

    @Test
    void shouldThrowConfigurationExceptionWhenGherkinDoctorFileHasNoDoctorSetting() {
        try (val fileUtilMock = mockStatic(FileUtil.class)) {
            // Given
            val mockGherkinDoctorFile = DoctorConfigurationTest.class.getClassLoader().getResourceAsStream("gherkin-doctor-no-rules-setting.yaml");
            fileUtilMock.when(() -> FileUtil.getInputStream(anyString())).thenReturn(mockGherkinDoctorFile);

            // When
            // Then
            assertThatThrownBy(DoctorConfiguration::loadDoctorSetting)
                    .isInstanceOf(ConfigurationException.class)
                    .hasMessage("Doctor Setting is missing or invalid.");
        }
    }
}
