package io.github.amiccoli.gherkindoctor.core;

import io.github.amiccoli.gherkindoctor.reader.FeatureReader;
import io.github.amiccoli.gherkindoctor.rule.Rule;
import io.github.amiccoli.gherkindoctor.rule.RuleError;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GherkinDoctorToolkit {

    private final FeatureReader featureReader;
    private final List<Rule> rules;

    /**
     * Lints the available Gherkin feature files by applying the configured rules.
     *
     * <p>The method:
     * <ul>
     *   <li>Reads all available Gherkin documents.</li>
     *   <li>Applies each validation rule to each document.</li>
     *   <li>Collects validation errors and groups them by feature file URI.</li>
     * </ul>
     *
     * <p>If no Gherkin documents are found, a message is logged, and an empty map is returned.
     *
     * @return a map where keys are feature file URIs and values are lists of {@link RuleError}.
     */
    public Map<String,List<RuleError>> lint() {
        var gherkinDocuments = featureReader.read();

        if (!gherkinDocuments.isEmpty()) {
            return gherkinDocuments.stream()
                    .flatMap(document -> rules.stream()
                            .flatMap(rule -> rule.apply(document).stream())
                    )
                    .collect(Collectors.groupingBy(RuleError::getUri));
        } else {
            log.info("Gherkin documents not found. Skipping validation.");
            return Collections.emptyMap();
        }
    }
}
