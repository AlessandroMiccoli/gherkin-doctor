package io.github.amiccoli.gherkindoctor.configuration;

import java.util.EnumMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseRuleStrategySettingTest {

    @Test
    void shouldConstructWithNoArgsConstructor() {
        // Given
        // When
        var setting = new BaseRuleSetting<>();

        // Then
        assertThat(setting.isActive()).isFalse();
        assertThat(setting.getMappings()).isEmpty();
    }

    @Test
    void shouldConstructWithAllArgsConstructor() {
        // Given
        var mappings = new EnumMap<>(GherkinElement.class);
        mappings.put(GherkinElement.FEATURE, 1L);

        // When
        var setting = new BaseRuleSetting<>(true, mappings);

        // Then
        assertThat(setting.isActive()).isTrue();
        assertThat(setting.getMappings())
                .isNotEmpty()
                .containsEntry(GherkinElement.FEATURE, 1L);
    }
}
