package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.rule.RuleLoader;
import java.util.List;
import lombok.val;

import static io.github.amiccoli.gherkindoctor.helper.MockRuleStrategyHelper.mockIndentationRuleStrategy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockRuleLoaderHelper {

    public static RuleLoader mockRuleLoader() {
        var mockRuleLoader = mock(RuleLoader.class);

        val mockRuleStrategy = mockIndentationRuleStrategy(1);
        given(mockRuleLoader.rules(any())).willReturn(List.of(mockRuleStrategy));

        return mockRuleLoader;
    }
}
