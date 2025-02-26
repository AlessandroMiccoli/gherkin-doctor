package io.github.amiccoli.gherkindoctor.factory;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import io.github.amiccoli.gherkindoctor.configuration.RulesConfiguration;
import io.github.amiccoli.gherkindoctor.rule.IndentationRule;
import java.util.EnumMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDoctorConfigurationHelper.mockGherkinDoctorConfiguration;
import static io.github.amiccoli.gherkindoctor.helper.MockRuleConstraintHelper.mockIndentationRuleConfiguration;
import static io.github.amiccoli.gherkindoctor.helper.RulesConfigurationHelper.mockRulesConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class IndentationRuleFactoryTest {

    private IndentationRuleFactory factory;
    private GherkinDoctorConfiguration mockGherkinDoctorConfig;
    private RulesConfiguration mockRulesConfig;

    @BeforeEach
    void setUp() {
        mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
        mockRulesConfig = mockRulesConfiguration();
        factory = new IndentationRuleFactory();
    }

    @Test
    void shouldBeAbleToCreateIndentationRuleWithExistingMappingsWhenRuleIsActive() {
        // Given
        given(mockGherkinDoctorConfig.getRules()).willReturn(mockRulesConfig);

        // When
        var rule = factory.create(mockGherkinDoctorConfig);

        // Then
        assertThat(rule)
                .isNotNull()
                .isInstanceOf(IndentationRule.class);

        assertThat(((IndentationRule) rule).getConstraints())
                .hasSize(2)
                .containsEntry(FEATURE, 1L)
                .containsEntry(GherkinElement.SCENARIO, 3L);
    }

    @Test
    void shouldCreteIndentationRuleErasingMappingWhenRuleIsInactive() {
        // Given
        given(mockGherkinDoctorConfig.getRules()).willReturn(mockRulesConfig);
        var mockIndentationRule = mockIndentationRuleConfiguration(
                new EnumMap<>(GherkinElement.class) {{
                    put(FEATURE, 1L);
                }}
        );
        given(mockRulesConfig.getIndentation()).willReturn(mockIndentationRule);
        given(mockIndentationRule.isActive()).willReturn(false);

        // When
        var rule = factory.create(mockGherkinDoctorConfig);

        // Then
        assertThat(rule)
                .isNotNull()
                .isInstanceOf(IndentationRule.class);

        assertThat(((IndentationRule) rule).getConstraints())
                .isEmpty();
    }
}
