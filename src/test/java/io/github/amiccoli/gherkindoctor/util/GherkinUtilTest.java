package io.github.amiccoli.gherkindoctor.util;

import io.cucumber.messages.types.Feature;
import io.github.amiccoli.gherkindoctor.exception.MissingGherkinElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDocumentHelper.mockValidGherkinDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GherkinUtilTest {

    @Test
    void shouldThrowMissingGherkinElementExceptionWhenFeatureUriIsMissing() {
        // Given
        Optional<String> fakeFeatureUri = Optional.empty();

        // When
        // Then
        assertThatThrownBy(() -> GherkinUtil.requireGherkinDocUri(fakeFeatureUri))
                .isInstanceOf(MissingGherkinElementException.class)
                .hasMessage("Feature URI is missing.");
    }

    @Test
    void shouldReturnFeatureUriWhenIsPresent() {
        // Given
        var fakeFeatureUri = Optional.of("anyFeatureUri");

        // When
        var maybeFeatureUri = GherkinUtil.requireGherkinDocUri(fakeFeatureUri);

        // Then
        assertThat(maybeFeatureUri)
                .isNotNull()
                .isEqualTo("anyFeatureUri");

    }

    @Test
    void shouldThrowMissingGherkinElementExceptionWhenFeatureIsMissing() {
        // Given
        Optional<Feature> fakeFeature = Optional.empty();

        // When
        // Then
        assertThatThrownBy(() -> GherkinUtil.requireFeature(fakeFeature))
                .isInstanceOf(MissingGherkinElementException.class)
                .hasMessage("Feature is missing.");
    }

    @Test
    void shouldReturnFeatureWhenIsPresent() {
        // Given
        var fakeFeature = mockValidGherkinDocument().getFeature();

        // When
        var maybeFeature = GherkinUtil.requireFeature(fakeFeature);

        // Then
        assertThat(maybeFeature)
                .isNotNull()
                .isEqualTo(fakeFeature.get());
    }

    @Test
    void shouldThrowMissingGherkinElementExceptionWhenColumnIsMissing() {
        // Given
        Optional<Long> fakeColumn = Optional.empty();

        // When
        // Then
        assertThatThrownBy(() -> GherkinUtil.requireColumn(fakeColumn))
                .isInstanceOf(MissingGherkinElementException.class)
                .hasMessage("Location column is missing.");
    }

    @Test
    void shouldReturnColumnWhenIsPresent() {
        // Given
        var fakeColumn = Optional.of(3L);

        // When
        var maybeColumn = GherkinUtil.requireColumn(fakeColumn);

        // Then
        assertThat(maybeColumn)
                .isNotNull()
                .isEqualTo(3L);

    }
}
