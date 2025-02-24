package io.github.amiccoli.gherkindoctor.factory;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.rule.IndentationRule;
import io.github.amiccoli.gherkindoctor.rule.Rule;
import org.springframework.stereotype.Component;

@Component
public class IndentationRuleFactory implements RuleFactory {

    @Override
    public Rule create(GherkinDoctorConfiguration gherkinDoctorConfiguration) {
        return new IndentationRule(gherkinDoctorConfiguration.getRules().getIndentation().getMappings());
    }
}
