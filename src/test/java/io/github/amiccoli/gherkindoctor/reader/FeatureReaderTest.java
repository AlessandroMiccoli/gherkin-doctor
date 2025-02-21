package io.github.amiccoli.gherkindoctor.reader;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDoctorConfigurationHelper.mockGherkinDoctorConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class FeatureReaderTest {

    @Test
    void shouldNotReadGherkinDocumentsWhenLocationHasNotFeatureFiles() {
        // Given
        var config = mockGherkinDoctorConfiguration();
        given(config.getFeatureLocation()).willReturn("features/empty");
        var featureReader = new FeatureReader(config);

        // When
        var gherkinDocuments = featureReader.read();

        // Then
        assertThat(gherkinDocuments).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"features/empty, 0, 0", "features/read, 1, 1"})
    void shouldLogGherkinDocumentsReadWhenReadProcessIsCompleted(String featureLocation, String filesRead, String docsRead) {
        // Given
        var listAppender = LoggerTestHelper.startLogger(FeatureReader.class);

        var config = mockGherkinDoctorConfiguration();
        given(config.getFeatureLocation()).willReturn(featureLocation);
        var featureReader = new FeatureReader(config);

        // When
        featureReader.read();

        // Then
        var logMessage = "Found [%s] Gherkin Documents in [%s] files.".formatted(filesRead, docsRead);
        LoggerTestHelper.verifyContainLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldLogParseErrorWhenFileNotWellFormatted() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(FeatureReader.class);

        var config = mockGherkinDoctorConfiguration();
        given(config.getFeatureLocation()).willReturn("features/invalid");
        var featureReader = new FeatureReader(config);

        // When
        featureReader.read();

        // Then
        var logMessage = "No Gherkin Document found for path [MockInvalidTest.feature]. Parse error: [(8:1): "
                + "expected: #EOF, #TableRow, #DocStringSeparator, #StepLine, #TagLine, #ExamplesLine, #ScenarioLine, "
                + "#RuleLine, #Comment, #Empty, got 'Feature: Mock invalid feature 2'].";
        LoggerTestHelper.verifyContainLog(listAppender, Level.ERROR, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }
}
