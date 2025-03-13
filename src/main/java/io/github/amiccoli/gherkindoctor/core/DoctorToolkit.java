/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.amiccoli.gherkindoctor.core;

import io.github.amiccoli.gherkindoctor.configuration.DoctorSetting;
import io.github.amiccoli.gherkindoctor.reader.FeatureReader;
import io.github.amiccoli.gherkindoctor.rule.RuleStrategy;
import io.github.amiccoli.gherkindoctor.rule.RuleError;
import io.github.amiccoli.gherkindoctor.rule.RuleLoader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DoctorToolkit {

    DoctorSetting doctorSetting;
    FeatureReader featureReader;
    RuleLoader ruleLoader;

    /**
     * Lints the available Gherkin feature files by applying the configured rules.
     *
     * <p>The method:
     * <ul>
     *   <li>Reads all available Gherkin documents.</li>
     *   <li>Applies each validation rule to each document.</li>
     *   <li>Log validation errors grouping them by feature file URI.</li>
     * </ul>
     */
    public void lint() {
        var gherkinDocuments = featureReader.read(doctorSetting.getFeatureLocation());

        if (!gherkinDocuments.isEmpty()) {
            List<RuleStrategy> ruleStrategies = ruleLoader.rules(doctorSetting.getRulesSetting());
            var linterError = gherkinDocuments.stream()
                    .flatMap(document -> ruleStrategies.stream()
                            .flatMap(rule -> rule.apply(document).stream())
                    )
                    .collect(Collectors.groupingBy(RuleError::getUri));

            printLintErrors(linterError);
        } else {
            log.info("Gherkin documents not found. Skipping validation.");
        }
    }

    private void printLintErrors(Map<String, List<RuleError>> lintErrors) {
        if (!lintErrors.isEmpty()) {
            lintErrors.forEach((uri, errors) -> {
                var fileName = uri.substring(uri.lastIndexOf('/') + 1);

                var errorMessages = errors.stream()
                        .map(error -> ("Line %d - %s - %s - %s %s").formatted(
                                error.getLine(),
                                error.getType(),
                                error.getKeyword(),
                                error.getActual(),
                                error.getExpected())
                        )
                        .collect(Collectors.joining(System.lineSeparator()));

                log.error(("Feature file: %s - Total errors: %d%s%s"
                        .formatted(fileName, errors.size(), System.lineSeparator(), errorMessages)));
            });
        } else {
            log.info("No lint errors found. Great job!");
        }
    }
}
