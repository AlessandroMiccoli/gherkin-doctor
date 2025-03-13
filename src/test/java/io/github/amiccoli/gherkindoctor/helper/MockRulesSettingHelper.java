package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.RulesSetting;

import static io.github.amiccoli.gherkindoctor.helper.MockRuleSettingHelper.mockIndentationRuleSetting;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockRulesSettingHelper {

    public static RulesSetting mockRulesSetting() {
        var config = mock(RulesSetting.class);
        var mockIndentationRuleSetting = mockIndentationRuleSetting();

        given(config.getIndentation()).willReturn(mockIndentationRuleSetting);

        return config;
    }
}
