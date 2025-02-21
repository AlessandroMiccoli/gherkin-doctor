package io.github.amiccoli.gherkindoctor.rule;

import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import lombok.*;

import static io.github.amiccoli.gherkindoctor.rule.RuleType.INDENTATION;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RuleError {
    String uri;
    RuleType type;
    GherkinElement keyword;
    String actual;
    String expected;

    public static RuleError createForIndentationRule(String docUri, GherkinElement keyword, Long actual, Long expected) {
        return RuleError.builder()
                .uri(docUri)
                .type(INDENTATION)
                .keyword(keyword)
                .actual("Actual %s indentation is %d.".formatted(keyword.name().toLowerCase(), actual))
                .expected("Expected %s indentation is %d.".formatted(keyword.name().toLowerCase(), expected))
                .build();
    }
}

