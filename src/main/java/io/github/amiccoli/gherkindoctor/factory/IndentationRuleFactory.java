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
