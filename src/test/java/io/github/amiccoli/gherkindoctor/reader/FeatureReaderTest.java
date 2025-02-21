package io.github.amiccoli.gherkindoctor.reader;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDoctorConfigurationHelper.mockGherkinDoctorConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class FeatureReaderTest {

    private GherkinDoctorConfiguration config;
    private FeatureReader reader;

    @BeforeEach
    void setUp() {
        config = mockGherkinDoctorConfiguration();
        reader = new FeatureReader(config);
    }

    @Test
    void shouldNotReadGherkinDocumentsWhenLocationHasNotFeatureFiles() {
        // Given
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

        given(config.getFeatureLocation()).willReturn(featureLocation);

        // When
        reader.read();

        // Then
        var logMessage = "Found [%s] Gherkin Documents in [%s] files.".formatted(filesRead, docsRead);
        LoggerTestHelper.verifyContainLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldLogParseErrorWhenFileNotWellFormatted() {
        // Given
        var listAppender = LoggerTestHelper.startLogger(FeatureReader.class);

        given(config.getFeatureLocation()).willReturn("features/invalid");

        // When
        reader.read();

        // Then
        var logMessage = "No Gherkin Document found for path [FakeInvalidTest.feature]. Parse error: [(8:1): "
                + "expected: #EOF, #TableRow, #DocStringSeparator, #StepLine, #TagLine, #ExamplesLine, #ScenarioLine, "
                + "#RuleLine, #Comment, #Empty, got 'Feature: Fake invalid feature 2'].";
        LoggerTestHelper.verifyContainLog(listAppender, Level.ERROR, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }
}
