package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.RulesConfiguration;

import static io.github.amiccoli.gherkindoctor.helper.MockRuleConstraintHelper.mockIndentationRuleConfiguration;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RulesConfigurationHelper {

    public static RulesConfiguration mockRulesConfiguration() {
        var config = mock(RulesConfiguration.class);
        var mockIndentationRuleConfig = mockIndentationRuleConfiguration();

        given(config.getIndentation()).willReturn(mockIndentationRuleConfig);

        return config;
    }
}
