package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.BaseRuleSetting;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import java.util.EnumMap;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockRuleSettingHelper {

    @SuppressWarnings("unchecked")
    public static BaseRuleSetting<Long> mockIndentationRuleSetting() {
        BaseRuleSetting<Long> rule = mock(BaseRuleSetting.class);
        given(rule.isActive()).willReturn(true);

        EnumMap<GherkinElement, Long> mappings = new EnumMap<>(GherkinElement.class);
        mappings.put(FEATURE, 0L);
        mappings.put(SCENARIO, 2L);

        given(rule.getMappings()).willReturn(mappings);
        return rule;
    }

    @SuppressWarnings("unchecked")
    public static BaseRuleSetting<Long> mockIndentationRuleSetting(EnumMap<GherkinElement, Long> mappings) {
        BaseRuleSetting<Long> rule = mock(BaseRuleSetting.class);
        given(rule.isActive()).willReturn(true);
        given(rule.getMappings()).willReturn(mappings);
        return rule;
    }
}
