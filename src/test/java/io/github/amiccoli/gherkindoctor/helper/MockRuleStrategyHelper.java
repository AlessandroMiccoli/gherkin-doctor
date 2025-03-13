package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import io.github.amiccoli.gherkindoctor.rule.IndentationRuleStrategy;
import io.github.amiccoli.gherkindoctor.rule.RuleError;
import io.github.amiccoli.gherkindoctor.rule.RuleType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockRuleStrategyHelper {

    public static IndentationRuleStrategy mockIndentationRuleStrategy() {
        return mockIndentationRuleStrategy(0);
    }

    public static IndentationRuleStrategy mockIndentationRuleStrategy(int errorSize) {
        var mockIndentationRuleStrategy = mock(IndentationRuleStrategy.class);

        var mappings = new EnumMap<GherkinElement, Long>(GherkinElement.class);
        mappings.put(FEATURE, 0L);
        mappings.put(SCENARIO, 2L);

        given(mockIndentationRuleStrategy.getConstraints()).willReturn(mappings);

        if(errorSize == 1) {
            var fakeRuleError = RuleError.builder()
                    .uri("anyUri")
                    .type(RuleType.INDENTATION)
                    .keyword(FEATURE)
                    .line(1L)
                    .actual("any actual.")
                    .expected("any expected.")
                    .build();

            given(mockIndentationRuleStrategy.apply(any())).willReturn(List.of(fakeRuleError));
        } else {
            given(mockIndentationRuleStrategy.apply(any())).willReturn(Collections.emptyList());
        }

        return mockIndentationRuleStrategy;
    }
}
