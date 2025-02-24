package io.github.amiccoli.gherkindoctor.factory;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.rule.Rule;

public interface RuleFactory {

    Rule create(GherkinDoctorConfiguration gherkinDoctorConfiguration);
}
