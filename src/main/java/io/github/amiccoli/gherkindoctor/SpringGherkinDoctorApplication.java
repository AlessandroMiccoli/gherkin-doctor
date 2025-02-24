package io.github.amiccoli.gherkindoctor;

import io.github.amiccoli.gherkindoctor.core.GherkinDoctorToolkit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class SpringGherkinDoctorApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringGherkinDoctorApplication.class, args);
    }

    private final GherkinDoctorToolkit toolkit;

    @Override
    public void run(String... args) {
//        var mappedRuleErrors = toolkit.lint();
//        log.error(mappedRuleErrors.toString());
    }
}

