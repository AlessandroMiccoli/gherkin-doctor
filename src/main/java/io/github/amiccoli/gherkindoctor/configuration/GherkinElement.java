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

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GherkinElement {
    FEATURE("feature"),
    FEATURE_DESCRIPTION("feature description"),
    FEATURE_TAG("feature tag"),

    RULE("rule"),
    RULE_DESCRIPTION("rule description"),
    RULE_TAG("rule tag"),
    RULE_BACKGROUND("rule background"),
    RULE_SCENARIO("rule scenario"),

    BACKGROUND("background"),
    BACKGROUND_DESCRIPTION("background description"),

    SCENARIO("scenario"),
    SCENARIO_DESCRIPTION("scenario description"),
    SCENARIO_TAG("scenario tag"),

    STEP("step"),
    STEP_DOC_STRING("step doc string"),
    STEP_DATA_TABLE("step data table"),

    EXAMPLES("example"),
    EXAMPLE_DESCRIPTION("example description"),
    EXAMPLE_TAG("example tag");

    private final String value;
}
