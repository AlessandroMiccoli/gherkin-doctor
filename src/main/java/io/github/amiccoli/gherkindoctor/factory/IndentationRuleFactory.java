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

package io.github.amiccoli.gherkindoctor.factory;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import io.github.amiccoli.gherkindoctor.rule.IndentationRule;
import io.github.amiccoli.gherkindoctor.rule.Rule;
import java.util.EnumMap;
import org.springframework.stereotype.Component;

@Component
public class IndentationRuleFactory implements RuleFactory {

    @Override
    public Rule create(GherkinDoctorConfiguration gherkinDoctorConfiguration) {
        var indentationRuleConfig = gherkinDoctorConfiguration.getRules().getIndentation();

        if (indentationRuleConfig.isActive()) {
            return new IndentationRule(indentationRuleConfig.getMappings());
        } else {
            return new IndentationRule(new EnumMap<>(GherkinElement.class));
        }
    }
}
