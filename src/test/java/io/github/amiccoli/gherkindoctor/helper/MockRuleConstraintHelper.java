package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.BaseRuleConfiguration;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import java.util.EnumMap;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockRuleConstraintHelper {

    @SuppressWarnings("unchecked")
    public static BaseRuleConfiguration<Long> mockIndentationRuleConfiguration() {
        BaseRuleConfiguration<Long> rule = mock(BaseRuleConfiguration.class);
        given(rule.isActive()).willReturn(true);

        EnumMap<GherkinElement, Long> mappings = new EnumMap<>(GherkinElement.class);
        mappings.put(FEATURE, 1L);
        mappings.put(SCENARIO, 3L);

        given(rule.getMappings()).willReturn(mappings);
        return rule;
    }

    @SuppressWarnings("unchecked")
    public static BaseRuleConfiguration<Long> mockIndentationRuleConfiguration(EnumMap<GherkinElement, Long> mappings) {
        BaseRuleConfiguration<Long> rule = mock(BaseRuleConfiguration.class);
        given(rule.isActive()).willReturn(true);
        given(rule.getMappings()).willReturn(mappings);
        return rule;
    }
}
