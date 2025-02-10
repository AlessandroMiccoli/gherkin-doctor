package io.github.amiccoli.gherkindoctor.reader;

import io.cucumber.gherkin.GherkinParser;
import io.cucumber.messages.types.*;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.exception.InvalidFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    public List<GherkinDocument> read() {
        val maybePaths = findPaths(configuration.getFeatureLocation());

        List<GherkinDocument> gherkinDocuments = new ArrayList<>();

        maybePaths.forEach(path -> {
            try {
                val featureContent = Files.readString(path);
                val envelopeSource = Envelope.of(
                        new Source(path.toString(),
                                featureContent,
                                TEXT_X_CUCUMBER_GHERKIN_PLAIN)
                );

                var envelopes = PARSER.parse(envelopeSource).toList();

                var parseError = maybeParseError(envelopes);

                if (parseError.isPresent()) {
                    log.error("Parse Error: {}", parseError.get().getMessage());
                } else {
                    log.info("No parse errors found. Proceeding with Gherkin Document collection.");

                    envelopes.stream()
                            .map(Envelope::getGherkinDocument)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .findFirst()
                            .ifPresentOrElse(
                                    gherkinDocuments::add,
                                    () -> log.error("No Gherkin Document found for path [{}]", path)
                            );
                }
            }
            catch (IOException exception) {
                throw new InvalidFileException("Not capable to walk through files of [%s] due to: [%s]."
                        .formatted(path.getFileName(), exception.getMessage()), exception
                );
            }
        });

        log.info("Found [{}] Gherkin Documents in [{}] files.", gherkinDocuments.size(), maybePaths.size());
        return gherkinDocuments;
    }

    private List<Path> findPaths(String inputPath) {
        val path = Path.of(inputPath);

        if (!Files.exists(path)) {
            throw new InvalidFileException("Files don't exist for [%s].".formatted(path.toAbsolutePath()));
        }

        try (val stream = Files.walk(path)) {
             return stream
                     .filter(Files::isRegularFile)
                     .filter(Files::isReadable)
                     .filter(p -> p.toString().endsWith(".feature"))
                     .toList();
        } catch (IOException exception) {
            throw new InvalidFileException("Not capable to walk through files of [%s]"
                    .formatted(path.getFileName()), exception
            );
        }
    }

    private Optional<ParseError> maybeParseError(List<Envelope> envelopes) {
        return envelopes.stream()
                .map(Envelope::getParseError)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
