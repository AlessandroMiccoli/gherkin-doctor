package io.github.amiccoli.gherkindoctor.rule;

import io.cucumber.messages.types.GherkinDocument;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import java.util.List;

/**
 * Interface representing a rule that can be applied to a Gherkin document.
 * <p>
 * This interface defines the structure for rules that operate on Gherkin documents,
 * allowing configuration to be set and applying validation logic to a given document.
 * Each rule should define how it is configured and how it interacts with the Gherkin document.
 */
public interface Rule {

    /**
     * Sets the configuration for the rule.
     * <p>
     * This method allows a specific configuration to be provided to the rule,
     * containing necessary constraints that the rule will use during its execution.
     * </p>
     *
     * @param gherkinDoctorConfiguration the configuration to be applied to the rule
     */
    void setConstraint(GherkinDoctorConfiguration gherkinDoctorConfiguration);

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
