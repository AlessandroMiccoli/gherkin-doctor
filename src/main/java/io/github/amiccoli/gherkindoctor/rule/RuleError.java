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

package io.github.amiccoli.gherkindoctor.rule;

import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import lombok.*;

import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RuleError {
    String uri;
    RuleType type;
    GherkinElement keyword;
    Long line;
    String actual;
    String expected;

    /**
     * Creates a {@link RuleError} instance specifically for indentation rule violations.
     *
     * @param docUri the URI of the Gherkin document where the rule violation occurred
     * @param keyword the {@link GherkinElement} for which indentation is validated
     * @param line the line where the error is located in the Gherkin document
     * @param actual the message with the actual indentation value found for the Gherkin element
     * @param expected the message with the expected indentation value for the Gherkin element
     * @return a {@link RuleError} describing the indentation rule violation
     */
    public static RuleError createForIndentationRule(
            String docUri,
            GherkinElement keyword,
            Long line,
            Long actual,
            Long expected)
    {
        return RuleError.builder()
                .uri(docUri)
                .type(INDENTATION)
                .keyword(keyword)
                .line(line)
                .actual("Actual %s indentation is %d.".formatted(keyword.getValue(), actual))
                .expected("Expected %s indentation is %d.".formatted(keyword.getValue(), expected))
                .build();
    }
}

