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

    /**
     * Creates a {@link RuleError} instance specifically for indentation rule violations.
     *
     * @param docUri the URI of the Gherkin document where the rule violation occurred
     * @param keyword the {@link GherkinElement} for which indentation is validated
     * @param actual the message with the actual indentation value found for the Gherkin element
     * @param expected the message with the expected indentation value for the Gherkin element
     * @return a {@link RuleError} describing the indentation rule violation
     */
    public static RuleError createForIndentationRule(String docUri, GherkinElement keyword, Long actual, Long expected) {
        return RuleError.builder()
                .uri(docUri)
                .type(INDENTATION)
                .keyword(keyword)
                .actual("Actual %s indentation is %d.".formatted(keyword.getValue(), actual))
                .expected("Expected %s indentation is %d.".formatted(keyword.getValue(), expected))
                .build();
    }
}

