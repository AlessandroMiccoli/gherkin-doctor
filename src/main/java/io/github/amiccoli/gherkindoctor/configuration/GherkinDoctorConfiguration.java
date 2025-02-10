package io.github.amiccoli.gherkindoctor.configuration;

import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gherkin-doctor")
public class GherkinDoctorConfiguration {

    private String featureLocation;

    @PostConstruct
    public void postConstruct() {
        if (featureLocation == null) {
            throw new ConfigurationException("Feature location is a mandatory property");
        }
    }
}
