package io.github.amiccoli.gherkindoctor.rule;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.factory.RuleFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RuleLoader {

    private final GherkinDoctorConfiguration configuration;
    private final List<RuleFactory> factories;

    @Bean
    public List<Rule> rules() {
        return factories.stream()
                .map(factory -> factory.create(configuration))
                .toList();
    }
}
