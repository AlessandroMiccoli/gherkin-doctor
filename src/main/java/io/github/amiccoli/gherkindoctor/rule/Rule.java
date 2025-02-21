package io.github.amiccoli.gherkindoctor.rule;

import io.cucumber.messages.types.GherkinDocument;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import java.util.List;

public interface Rule {

    void setConstraint(GherkinDoctorConfiguration gherkinDoctorConfiguration);

    List<RuleError> apply(GherkinDocument gherkinDocument);
}
