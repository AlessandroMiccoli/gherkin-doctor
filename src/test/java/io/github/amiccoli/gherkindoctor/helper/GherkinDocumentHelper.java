package io.github.amiccoli.gherkindoctor.helper;

import io.cucumber.messages.types.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GherkinDocumentHelper {

    public static GherkinDocument mockValidGherkinDocument() {
        var mockGherkinDocument = mock(GherkinDocument.class);
        var mockFeature = mockFeature(true);

        given(mockGherkinDocument.getFeature()).willReturn(Optional.of(mockFeature));
        given(mockGherkinDocument.getUri()).willReturn(Optional.of("FakeIndentationRuleTest.feature"));

        return mockGherkinDocument;
    }

    public static GherkinDocument mockInvalidGherkinDocument() {
        var mockGherkinDocument = mock(GherkinDocument.class);
        var mockFeature = mockFeature(false);

        given(mockGherkinDocument.getFeature()).willReturn(Optional.of(mockFeature));
        given(mockGherkinDocument.getUri()).willReturn(Optional.of("FakeIndentationRuleTest.feature"));

        return mockGherkinDocument;
    }

    private static Feature mockFeature(boolean isValid) {
        var mockFeature = mock(Feature.class);
        var mockLocation = mock(Location.class);
        var mockFeatureChild = mock(FeatureChild.class);
        var mockScenario = mockScenario(isValid);
        var mockBackground = mockBackground(isValid);

        given(mockFeature.getLocation()).willReturn(mockLocation);
        given(mockLocation.getColumn()).willReturn(Optional.of(isValid ? 1L : 2L));
        given(mockFeature.getChildren()).willReturn(List.of(mockFeatureChild));
        given(mockFeatureChild.getScenario()).willReturn(Optional.of(mockScenario));
        given(mockFeatureChild.getBackground()).willReturn(Optional.of(mockBackground));

        return mockFeature;
    }

    private static Background mockBackground(boolean isValid) {
        var mockBackground = mock(Background.class);
        var mockLocation = mock(Location.class);

        given(mockBackground.getLocation()).willReturn(mockLocation);
        given(mockLocation.getColumn()).willReturn(Optional.of(isValid ? 3L : 5L));

        return mockBackground;
    }

    private static Scenario mockScenario(boolean isValid) {
        var mockScenario = mock(Scenario.class);
        var mockLocation = mock(Location.class);

        given(mockScenario.getLocation()).willReturn(mockLocation);
        given(mockLocation.getColumn()).willReturn(Optional.of(isValid ? 3L : 6L));

        return mockScenario;
    }
}
