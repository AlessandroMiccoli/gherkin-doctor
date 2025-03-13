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

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GherkinElement {
    FEATURE("feature"),
//    FEATURE_DESCRIPTION("feature description"),
//    FEATURE_TAG("feature tag"),

    RULE("rule"),
//    RULE_DESCRIPTION("rule description"),
//    RULE_TAG("rule tag"),
//    RULE_BACKGROUND("rule background"),
//    RULE_SCENARIO("rule scenario"),

    BACKGROUND("background"),
//    BACKGROUND_DESCRIPTION("background description"),

    SCENARIO("scenario"),
//    SCENARIO_DESCRIPTION("scenario description"),
//    SCENARIO_TAG("scenario tag"),

    STEP("step"),
//    STEP_DOC_STRING("step doc string"),
//    STEP_DATA_TABLE("step data table"),

    EXAMPLES("examples");
//    EXAMPLE_DESCRIPTION("example description"),
//    EXAMPLE_TAG("example tag");

    private final String value;

    /**
     * Creates a {@link GherkinElement} from a string value.
     * <p>
     * This method attempts to match the provided string to a corresponding {@link GherkinElement}
     * by comparing it with the values of all available enum constants. If no match is found,
     * a {@link ConfigurationException} is thrown.
     * </p>
     *
     * @param value the string representation of the {@link GherkinElement}.
     * @return the matching {@link GherkinElement} enum constant.
     * @throws ConfigurationException if no matching {@link GherkinElement} is found for the given value.
     */
    @JsonCreator
    public static GherkinElement from(String value) {
        return Arrays.stream(GherkinElement.values())
                .filter(element -> element.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new ConfigurationException(
                        "Gherkin element not found for [%s].".formatted(value))
                );
    }
}
