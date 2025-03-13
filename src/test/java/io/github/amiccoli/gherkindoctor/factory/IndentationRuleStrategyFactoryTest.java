package io.github.amiccoli.gherkindoctor.factory;

import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import io.github.amiccoli.gherkindoctor.configuration.RulesSetting;
import io.github.amiccoli.gherkindoctor.rule.IndentationRuleStrategy;
import java.util.EnumMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.helper.MockRuleSettingHelper.mockIndentationRuleSetting;
import static io.github.amiccoli.gherkindoctor.helper.MockRulesSettingHelper.mockRulesSetting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class IndentationRuleStrategyFactoryTest {

    private IndentationRuleFactory factory;
    private RulesSetting mockRulesSetting;

    @BeforeEach
    void setUp() {
        mockRulesSetting = mockRulesSetting();
        factory = new IndentationRuleFactory();
    }

    @Test
    void shouldBeAbleToCreateIndentationRuleWithExistingMappingsWhenRuleIsActive() {
        // Given

        // When
        var rule = factory.create(mockRulesSetting);

        // Then
        assertThat(rule)
                .isNotNull()
                .isInstanceOf(IndentationRuleStrategy.class);

        assertThat(((IndentationRuleStrategy) rule).getConstraints())
                .hasSize(2)
                .containsEntry(FEATURE, 0L)
                .containsEntry(GherkinElement.SCENARIO, 2L);
    }

    @Test
    void shouldCreteIndentationRuleErasingMappingWhenRuleIsInactive() {
        // Given
        var mockIndentationRule = mockIndentationRuleSetting(
                new EnumMap<>(GherkinElement.class) {{
                    put(FEATURE, 1L);
                }}
        );
        given(mockRulesSetting.getIndentation()).willReturn(mockIndentationRule);
        given(mockIndentationRule.isActive()).willReturn(false);

        // When
        var rule = factory.create(mockRulesSetting);

        // Then
        assertThat(rule)
                .isNotNull()
                .isInstanceOf(IndentationRuleStrategy.class);

        assertThat(((IndentationRuleStrategy) rule).getConstraints())
                .isEmpty();
    }
}
