package io.github.amiccoli.gherkindoctor.helper;

import io.cucumber.messages.types.*;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.reader.FeatureReader;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class FeatureReaderHelper {

    public static GherkinDocument mockValidGherkinDocumentIndentationRule() {
        var mockGherkinDocument = mock(GherkinDocument.class);
        var mockFeature = mock(Feature.class);
        var mockFeatureLocation = mock(Location.class);
        var mockFeatureChild = mock(FeatureChild.class);
        var mockScenario = mock(Scenario.class);
        var mockScenarioLocation = mock(Location.class);

        given(mockGherkinDocument.getFeature()).willReturn(Optional.of(mockFeature));
        given(mockGherkinDocument.getUri()).willReturn(Optional.of("FakeIndentationRuleTest.feature"));
        given(mockFeature.getLocation()).willReturn(mockFeatureLocation);
        given(mockFeatureLocation.getColumn()).willReturn(Optional.of(1L));

        given(mockFeature.getChildren()).willReturn(List.of(mockFeatureChild));
        given(mockFeatureChild.getScenario()).willReturn(Optional.of(mockScenario));
        given(mockScenario.getLocation()).willReturn(mockScenarioLocation);
        given(mockScenarioLocation.getColumn()).willReturn(Optional.of(3L));

        return mockGherkinDocument;
    }

    public static GherkinDocument mockInvalidGherkinDocumentForFeatureIndentationRule() {
        var mockGherkinDocument = mock(GherkinDocument.class);
        var mockFeature = mock(Feature.class);
        var mockLocation = mock(Location.class);

        given(mockGherkinDocument.getFeature()).willReturn(Optional.of(mockFeature));
        given(mockGherkinDocument.getUri()).willReturn(Optional.of("FakeIndentationRuleTest.feature"));
        given(mockFeature.getLocation()).willReturn(mockLocation);
        given(mockLocation.getColumn()).willReturn(Optional.of(2L));

        return mockGherkinDocument;
    }

    public static GherkinDocument mockInvalidGherkinDocumentForBackgroundIndentationRule() {
        var mockGherkinDocument = mock(GherkinDocument.class);
        var mockFeature = mock(Feature.class);
        var mockFeatureChildren = mock(FeatureChild.class);
        var mockBackground = mock(Background.class);
        var mockLocation = mock(Location.class);

        given(mockGherkinDocument.getFeature()).willReturn(Optional.of(mockFeature));
        given(mockGherkinDocument.getUri()).willReturn(Optional.of("FakeIndentationRuleTest.feature"));
        given(mockFeature.getChildren()).willReturn(List.of(mockFeatureChildren));
        given(mockFeatureChildren.getBackground()).willReturn(Optional.of(mockBackground));
        given(mockBackground.getLocation()).willReturn(mockLocation);
        given(mockLocation.getColumn()).willReturn(Optional.of(5L));

        return mockGherkinDocument;
    }

    public static GherkinDocument mockInvalidGherkinDocumentForScenarioIndentationRule() {
        var mockGherkinDocument = mock(GherkinDocument.class);
        var mockFeature = mock(Feature.class);
        var mockFeatureChildren = mock(FeatureChild.class);
        var mockScenario = mock(Scenario.class);
        var mockLocation = mock(Location.class);

        given(mockGherkinDocument.getFeature()).willReturn(Optional.of(mockFeature));
        given(mockGherkinDocument.getUri()).willReturn(Optional.of("FakeIndentationRuleTest.feature"));
        given(mockFeature.getChildren()).willReturn(List.of(mockFeatureChildren));
        given(mockFeatureChildren.getScenario()).willReturn(Optional.of(mockScenario));
        given(mockScenario.getLocation()).willReturn(mockLocation);
        given(mockLocation.getColumn()).willReturn(Optional.of(6L));

        return mockGherkinDocument;
    }

    public static GherkinDocument spyGherkinDocument() {
        GherkinDoctorConfiguration mockGherkinDoctorConfiguration = mock(GherkinDoctorConfiguration.class);
        given(mockGherkinDoctorConfiguration.getFeatureLocation()).willReturn("features");
        FeatureReader featureReader = new FeatureReader(mockGherkinDoctorConfiguration);
        return spy(featureReader.read().get(0));
    }
}
