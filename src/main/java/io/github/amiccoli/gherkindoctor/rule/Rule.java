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

import io.cucumber.messages.types.GherkinDocument;
import java.util.List;

public interface Rule {

    /**
     * Applies the rule to a given Gherkin document.
     * <p>
     * This method performs the validation logic defined by the rule,
     * checking the provided Gherkin document for compliance. It may return
     * a list of rule errors encountered during the application (if present).
     *
     * @param gherkinDocument the Gherkin document to which the rule will be applied
     * @return a list of rule errors, or an empty list if no errors are found
     */
    List<RuleError> apply(GherkinDocument gherkinDocument);
}
