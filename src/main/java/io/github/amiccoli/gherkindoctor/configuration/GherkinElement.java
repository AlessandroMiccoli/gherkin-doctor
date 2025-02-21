package io.github.amiccoli.gherkindoctor.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GherkinElement {
    FEATURE("feature"),
    FEATURE_DESCRIPTION("feature description"),
    FEATURE_TAG("feature tag"),

    RULE("rule"),
    RULE_DESCRIPTION("rule description"),
    RULE_TAG("rule tag"),
    RULE_BACKGROUND("rule background"),
    RULE_SCENARIO("rule scenario"),

    BACKGROUND("background"),
    BACKGROUND_DESCRIPTION("background description"),

    SCENARIO("scenario"),
    SCENARIO_DESCRIPTION("scenario description"),
    SCENARIO_TAG("scenario tag"),

    STEP("step"),
    STEP_DOC_STRING("step doc string"),
    STEP_DATA_TABLE("step data table"),

    EXAMPLES("example"),
    EXAMPLE_DESCRIPTION("example description"),
    EXAMPLE_TAG("example tag");

    private final String value;
}
