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

package io.github.amiccoli.gherkindoctor;

import io.github.amiccoli.gherkindoctor.core.GherkinDoctorToolkit;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
@ShellComponent
public class SpringGherkinDoctorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringGherkinDoctorApplication.class, args);
    }

    private final GherkinDoctorToolkit toolkit;

    @ShellMethod(key = "gherkin-doctor lint", value = "Analyzes Gherkin documents for rule violations and reports issues.")
    public void lint() {
        var mappedRuleErrors = toolkit.lint();

        if(!mappedRuleErrors.isEmpty()) {
            mappedRuleErrors.forEach((uri, errors) -> {
                var fileName = uri.substring(uri.lastIndexOf('/') + 1);

                var errorMessages = errors.stream()
                        .map(error -> ("Line %d - %s - %s -  %s %s").formatted(
                                error.getLine(),
                                error.getType(),
                                error.getKeyword(),
                                error.getActual(),
                                error.getExpected())
                        )
                        .collect(Collectors.joining(System.lineSeparator()));

                log.error("Feature file: {} - Total errors: {}{}{}",
                        fileName,
                        errors.size(),
                        System.lineSeparator(),
                        errorMessages);
            });
        }
    }
}

