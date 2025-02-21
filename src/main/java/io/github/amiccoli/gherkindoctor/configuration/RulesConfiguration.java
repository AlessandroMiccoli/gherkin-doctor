package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import java.util.EnumMap;
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

    public void validate() {
        validateIndentation();
    }

    private void validateIndentation() {
        if (indentation.isActive()) {
            if (indentation.getMappings().isEmpty()) {
                throw new ConfigurationException("Indentation rule must have at least one mapping when is active.");
            }
        } else {
            log.info("Indentation rule is inactive. Skipping validation.");
            indentation.setMappings(new EnumMap<>(GherkinElement.class));
        }
    }
}
