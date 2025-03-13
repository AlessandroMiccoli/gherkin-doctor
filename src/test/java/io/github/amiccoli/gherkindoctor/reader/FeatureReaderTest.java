package io.github.amiccoli.gherkindoctor.reader;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.configuration.DoctorSetting;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.amiccoli.gherkindoctor.helper.MockDoctorSettingHelper.mockDoctorSetting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class FeatureReaderTest {

    private DoctorSetting mockDoctorSetting;
    private FeatureReader reader;

    @BeforeEach
    void setUp() {
        mockDoctorSetting = mockDoctorSetting();
        reader = new FeatureReader();
    }

    @Test
    void shouldNotReadGherkinDocumentsWhenLocationHasNotFeatureFiles() {
        // Given
        given(mockDoctorSetting.getFeatureLocation()).willReturn("features/empty");

        // When
        var gherkinDocuments = reader.read(mockDoctorSetting.getFeatureLocation());

        // Then
        assertThat(gherkinDocuments).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"features/empty, 0, 0", "features/read, 1, 1"})
    void shouldLogGherkinDocumentsReadWhenReadProcessIsCompleted(String featureLocation, String filesRead, String docsRead) {
        // Given
        var listAppender = LoggerTestHelper.startLogger(FeatureReader.class);

        given(mockDoctorSetting.getFeatureLocation()).willReturn(featureLocation);

        // When
        reader.read(mockDoctorSetting.getFeatureLocation());

        // Then
        var logMessage = "Found [%s] Gherkin Documents in [%s] files.".formatted(filesRead, docsRead);
        LoggerTestHelper.verifyContainLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldLogParseErrorWhenFileNotWellFormatted() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(FeatureReader.class);

        given(mockDoctorSetting.getFeatureLocation()).willReturn("features/invalid");

        // When
        reader.read(mockDoctorSetting.getFeatureLocation());

        // Then
        var logMessage = "No Gherkin Document found for path [FakeInvalidTest.feature]. Parse error: [(8:1): "
                + "expected: #EOF, #TableRow, #DocStringSeparator, #StepLine, #TagLine, #ExamplesLine, #ScenarioLine, "
                + "#RuleLine, #Comment, #Empty, got 'Feature: Fake invalid feature 2'].";
        LoggerTestHelper.verifyContainLog(listAppender, Level.ERROR, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }
}
