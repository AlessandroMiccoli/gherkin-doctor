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
