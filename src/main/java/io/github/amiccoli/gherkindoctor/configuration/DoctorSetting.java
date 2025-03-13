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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DoctorSetting {

    @JsonProperty("feature-location")
    private String featureLocation;

    @JsonProperty("rules")
    private RulesSetting rulesSetting;

    /**
     * Validates the configuration settings for the feature location and rules.
     *
     * @throws IllegalArgumentException if the feature location is not set or if rule validation fails.
     */
    public void validate() {
        try {
            if (featureLocation == null) {
                throw new IllegalArgumentException("Feature resource location is a mandatory property.");
            }

            rulesSetting.validate();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }
}
