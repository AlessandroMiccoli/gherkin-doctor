package io.github.amiccoli.gherkindoctor.util;

import io.github.amiccoli.gherkindoctor.exception.InvalidFileException;
import java.io.IOException;
import java.nio.file.Path;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUtilTest {

    @Test
    void shouldThrowInvalidFileExceptionWhenResourceURLDoesntExist() {
        // Given
        val invalidRelativePath = "invalid/path/to/features";

        // When
        // Then
        assertThatThrownBy(() -> FileUtil.findPaths(invalidRelativePath))
                .isInstanceOf(InvalidFileException.class)
                .hasMessage("Resource not found for path [invalid/path/to/features].");
    }

    @ParameterizedTest
    @CsvSource({"features/valid, 1", "features/empty, 0"})
    void shouldFindOnlyFeatureFileWhenValidLocationIsProvided(String relativePath, int expectedFeatureFileFound) {
        // Given
        // When
        var paths = FileUtil.findPaths(relativePath);

        // Then
        assertThat(paths).hasSize(expectedFeatureFileFound);

        if(!paths.isEmpty()) {
            assertThat(paths.get(0).toString()).endsWith("/features/valid/FakeTest.feature");
        }
    }

    @Test
    void shouldReadFeatureFileWhenValidLocationIsProvided() {
        // Given
        var resource = "features/read/FakeReadTest.feature";
        val relativePath = requireNonNull(
                FileUtilTest.class.getClassLoader().getResource(resource),
                "Resource not found for [%s].".formatted(resource)
        ).getPath();

        // When
        val content = FileUtil.readFile(Path.of(relativePath));

        // Then
        assertThat(content)
                .as("Content of the feature file should match the expected text")
                .isNotNull()
                .hasToString("Feature: Fake read feature");

    }

    @Test
    void shouldHandleIOExceptionWhenReadingFeatureFileIsNotPossible() {
        // Given
        val invalidPath = Path.of("features/MockNotPresentTest.feature");

        // When
        // Then
        assertThatThrownBy(() -> FileUtil.readFile(invalidPath))
                .isInstanceOf(InvalidFileException.class)
                .hasMessage("Not capable to read file [features/MockNotPresentTest.feature].");

    }

    @Test
    void shouldGetInputStreamWhenValidFileIsProvided() throws IOException {
        // Given
        // When
        val inputStream = FileUtil.getInputStream("gherkin-doctor-valid.yaml");

        // Then
        assertThat(inputStream).isNotNull();
    }

    @Test
    void shouldThrowInvalidFileExceptionWHneFileIsMissing() throws IOException {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> FileUtil.getInputStream("missing-gherkin-doctor.yaml"))
                .isInstanceOf(InvalidFileException.class)
                .hasMessage("File not found: [missing-gherkin-doctor.yaml].");
    }
}
