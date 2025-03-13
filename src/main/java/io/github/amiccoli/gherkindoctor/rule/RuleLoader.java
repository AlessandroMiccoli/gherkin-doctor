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

import io.github.amiccoli.gherkindoctor.configuration.RulesSetting;
import io.github.amiccoli.gherkindoctor.factory.RuleFactory;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RuleLoader {

    private final List<RuleFactory> factories;

    /**
     * Creates a list of {@link RuleStrategy} instances based on the provided {@link RulesSetting}.
     * <p>
     * Each registered {@link RuleFactory} is used to generate a corresponding {@link RuleStrategy}
     * using the given rules configuration.
     * </p>
     *
     * @param rulesSetting the configuration settings used to create rule strategies.
     * @return a list of {@link RuleStrategy} instances based on the provided settings.
     */
    public List<RuleStrategy> rules(RulesSetting rulesSetting) {
        return factories.stream()
                .map(factory -> factory.create(rulesSetting))
                .toList();
    }
}
