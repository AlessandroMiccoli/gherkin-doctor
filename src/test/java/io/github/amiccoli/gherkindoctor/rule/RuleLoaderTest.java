package io.github.amiccoli.gherkindoctor.rule;

import io.github.amiccoli.gherkindoctor.configuration.DoctorConfiguration;
import io.github.amiccoli.gherkindoctor.configuration.DoctorSetting;
import io.github.amiccoli.gherkindoctor.factory.RuleFactory;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.MockDoctorConfigurationHelper.mockDoctorConfiguration;
import static io.github.amiccoli.gherkindoctor.helper.MockDoctorSettingHelper.mockDoctorSetting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RuleLoaderTest {

    DoctorSetting mockDoctorSetting;

    @BeforeEach
    void setUp() {
        mockDoctorSetting = mockDoctorSetting();
    }

    @Test
    void shouldReturnNoRulesWhenThereAreNoFactories() {
        // Given
        var ruleLoader = new RuleLoader(Collections.emptyList());

        // When
        var rules = ruleLoader.rules(mockDoctorSetting.getRulesSetting());

        // Then
        assertThat(rules).isEmpty();
    }

    @Test
    void shouldReturnTheRuleWhenThereIsAnActiveFactory() {
        // Given
        var mockFactory = mock(RuleFactory.class);
        var mockRuleStrategy = mock(RuleStrategy.class);
        given(mockFactory.create(mockDoctorSetting.getRulesSetting()))
                .willReturn(mockRuleStrategy);

        var ruleLoader = new RuleLoader(List.of(mockFactory));

        // When
        var rules = ruleLoader.rules(mockDoctorSetting.getRulesSetting());

        // Then
        assertThat(rules)
                .hasSize(1)
                .containsExactly(mockRuleStrategy);

        verify(mockFactory).create(mockDoctorSetting.getRulesSetting());
    }
}
