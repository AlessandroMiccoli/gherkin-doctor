package io.github.amiccoli.gherkindoctor.core;

import ch.qos.logback.classic.Level;
import io.github.amiccoli.gherkindoctor.configuration.*;
import io.github.amiccoli.gherkindoctor.helper.LoggerTestHelper;
import io.github.amiccoli.gherkindoctor.reader.FeatureReader;
import io.github.amiccoli.gherkindoctor.rule.RuleLoader;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.MockDoctorSettingHelper.mockDoctorSetting;
import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDocumentHelper.mockInvalidGherkinDocument;
import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDocumentHelper.mockValidGherkinDocument;
import static io.github.amiccoli.gherkindoctor.helper.MockRuleLoaderHelper.mockRuleLoader;
import static io.github.amiccoli.gherkindoctor.helper.MockRuleStrategyHelper.mockIndentationRuleStrategy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DoctorToolkitTest {

    private DoctorSetting mockDoctorSetting;
    private FeatureReader mockReader;
    private RuleLoader mockRuleLoader;

    @BeforeEach
    void setUp() {
        mockDoctorSetting = mockDoctorSetting();
        mockReader = mock(FeatureReader.class);
        mockRuleLoader = mockRuleLoader();
    }

    @Test
    void shouldInfoLogWhenLintingNoGherkinDocument() {
        // Given
        val listAppender = LoggerTestHelper.startLogger(DoctorToolkit.class);

        given(mockDoctorSetting.getFeatureLocation()).willReturn("features/empty");

        val toolkit = new DoctorToolkit(mockDoctorSetting, mockReader, mockRuleLoader);

        // When
        toolkit.lint();

        // Then
        val logMessage = "Gherkin documents not found. Skipping validation.";
        LoggerTestHelper.verifyLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldInfoLogWhenLintingGherkinDocumentWithNoErrors() {
        // Given
        val listAppender = LoggerTestHelper.startLogger(DoctorToolkit.class);

        val mockGherkinDocument = mockValidGherkinDocument();
        given(mockReader.read(anyString())).willReturn(List.of(mockGherkinDocument));

        val mockRuleStrategy = mockIndentationRuleStrategy();
        given(mockRuleLoader.rules(any())).willReturn(List.of(mockRuleStrategy));

        val toolkit = new DoctorToolkit(mockDoctorSetting, mockReader, mockRuleLoader);

        // When
        toolkit.lint();

        // Then
        val logMessage = "No lint errors found. Great job!";
        LoggerTestHelper.verifyLog(listAppender, Level.INFO, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }

    @Test
    void shouldErrorLogWhenLintingInvalidGherkinDocument() {
        // Given
        val listAppender = LoggerTestHelper.startLogger(DoctorToolkit.class);

        val mockGherkinDocument = mockInvalidGherkinDocument();
        given(mockReader.read(anyString())).willReturn(List.of(mockGherkinDocument));

        val toolkit = new DoctorToolkit(mockDoctorSetting, mockReader, mockRuleLoader);

        // When
        toolkit.lint();

        // Then
        val logMessage = "Feature file: anyUri - Total errors: 1\nLine 1 - INDENTATION - FEATURE - any actual. any expected.";
        LoggerTestHelper.verifyLog(listAppender, Level.ERROR, logMessage);
        LoggerTestHelper.stopLogger(listAppender);
    }
}
