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

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RulesSetting {

    private BaseRuleSetting<Long> indentation;

    /**
     * Validates the configured rules. Currently, this includes:
     * <ul>
     *     <li>Indentation rule</li>
     * </ul>
     * <p>
     * If the rule is active but has no mappings, an {@link IllegalArgumentException} is thrown.
     * If the rule is inactive, validation is skipped, and a log message is recorded.
     * </p>
     *
     * @throws IllegalArgumentException if the indentation rule is active but has no mappings.
     */
    public void validate() throws IllegalArgumentException {
        validateIndentation();
    }

    private void validateIndentation () throws IllegalArgumentException {
        if (indentation.isActive()) {
            if (indentation.getMappings().isEmpty()) {
                throw new IllegalArgumentException("Indentation rule must have at least one mapping when is active.");
            }
        } else {
            log.info("Indentation rule is inactive. Skipping validation of configured properties.");
        }
    }
}
