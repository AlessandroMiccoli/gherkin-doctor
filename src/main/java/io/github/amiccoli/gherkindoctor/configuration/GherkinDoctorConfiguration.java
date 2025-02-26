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

package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the Gherkin Doctor application.
 * <p>
 * This class holds the configuration for the feature file location, which is
 * required for reading and parsing Gherkin documents. The configuration is
 * automatically mapped from properties prefixed with <code>gherkin-doctor</code>.
 * </p>
 *
 * <p>
 * Example configuration in <code>application.properties</code>:
 * <pre>
 * gherkin-doctor.feature-location=/path/to/features
 * </pre>
 * </p>
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "gherkin-doctor")
@ToString
public class GherkinDoctorConfiguration {

    private String featureLocation;
    private RulesConfiguration rules;

    /**
     * Initializes the class after the dependencies are injected.
     * <p>
     * This method is called automatically after the object is constructed and
     * all dependencies are injected by the framework. It checks whether the
     * feature location is provided and validates the configuration rules.
     * </p>
     *
     * @throws ConfigurationException if the feature location is not set (null) or if validation fails.
     */
    @PostConstruct
    public void postConstruct() {
        try {
            if (featureLocation == null) {
                throw new IllegalArgumentException("Feature resource location is a mandatory property.");
            }

            rules.validate();
        } catch (IllegalArgumentException exception) {
            throw new ConfigurationException(exception.getMessage());
        }
    }
}
