package io.github.amiccoli.gherkindoctor.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@ToString
public class RulesConfiguration {

    BaseRuleConfiguration<Long> indentation;

    /**
     * Validates the indentation rule configuration.
     * <p>
     * If the indentation rule is active but has no mappings, a {@link IllegalArgumentException} is thrown.
     * Otherwise, if the rule is inactive, validation is skipped.
     *
     *
     * @throws IllegalArgumentException if the indentation rule is active but has no mappings.
     */
    public void validate() throws IllegalArgumentException {
        validateIndentation();
    }

    private void validateIndentation () throws IllegalArgumentException {
        if (indentation.isActive()) {
            if (indentation.getMappings().isEmpty()) {
                throw new IllegalArgumentException("Indentation rule must have at least one mapping when is active.");
            }
        } else {
            log.info("Indentation rule is inactive. Skipping validation of configured properties.");
        }
    }
}
