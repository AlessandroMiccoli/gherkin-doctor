package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the Gherkin Doctor application.
 * <p>
 * This class holds the configuration for the feature file location, which is
 * required for reading and parsing Gherkin documents. The configuration is
 * automatically mapped from properties prefixed with <code>gherkin-doctor</code>.
 * </p>
 *
 * <p>
 * Example configuration in <code>application.properties</code>:
 * <pre>
 * gherkin-doctor.feature-location=/path/to/features
 * </pre>
 * </p>
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "gherkin-doctor")
@ToString
public class GherkinDoctorConfiguration {

    private String featureLocation;
    private RulesConfiguration rules;

    /**
     * Checks if the mandatory configuration properties are set.
     *
     * @throws ConfigurationException if <code>featureLocation</code> is null
     */
    @PostConstruct
    public void postConstruct() {
        if (featureLocation == null) {
            throw new ConfigurationException("Feature resource location is a mandatory property.");
        }

        rules.validate();
    }
}
