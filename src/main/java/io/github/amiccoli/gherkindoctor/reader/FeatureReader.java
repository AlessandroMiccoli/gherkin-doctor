package io.github.amiccoli.gherkindoctor.reader;

import io.cucumber.gherkin.GherkinParser;
import io.cucumber.messages.types.*;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.util.FileUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import static io.cucumber.messages.types.SourceMediaType.TEXT_X_CUCUMBER_GHERKIN_PLAIN;

@Slf4j
@Component
@AllArgsConstructor
public class FeatureReader {

    private final GherkinDoctorConfiguration configuration;

    private static final GherkinParser PARSER = GherkinParser.builder()
            .includeSource(false)
            .includePickles(false)
            .build();

    /**
     * Reads and parses Gherkin feature files from the configured feature location.
     * <p>
     * This method searches recursively for all `.feature` files, parses their content using
     * the Gherkin parser, and collects the resulting {@link GherkinDocument} objects.
     * If a file cannot be parsed into a GherkinDocument, an error is logged and the file is skipped.
     * </p>
     *
     * @return a list of {@link GherkinDocument} objects representing the parsed feature files
     */
    public List<GherkinDocument> read() {
        val maybePaths = FileUtil.findPaths(configuration.getFeatureLocation());

        val gherkinDocuments = maybePaths.stream()
                .map(path -> {

                    val featureContent = FileUtil.readFile(path);
                    val envelopeSource = Envelope.of(
                            new Source(path.toString(),
                                    featureContent,
                                    TEXT_X_CUCUMBER_GHERKIN_PLAIN)
                    );

                    val envelopes = PARSER.parse(envelopeSource).toList();

                    return envelopes.stream()
                            .map(Envelope::getGherkinDocument)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .findFirst()
                            .orElseGet(
                                    () -> {
                                        log.error("No Gherkin Document found for path [{}]. Parse error: [{}].",
                                                path.getFileName(),
                                                maybeParseErrorMessage(envelopes)
                                        );
                                        return null;
                                    }
                            );
                })
                .filter(Objects::nonNull)
                .toList();

        log.info("Found [{}] Gherkin Documents in [{}] files.", gherkinDocuments.size(), maybePaths.size());
        return gherkinDocuments;
    }

    private String maybeParseErrorMessage(List<Envelope> envelopes) {
        return envelopes.stream()
                .map(Envelope::getParseError)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .map(ParseError::getMessage)
                .orElse("Unknown error");
    }
}
