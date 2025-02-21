package io.github.amiccoli.gherkindoctor.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseRuleConfigurationTest {

    @Test
    void shouldSetDefaultValues() {
        // Given
        // When
        var config = new BaseRuleConfiguration<>();

        // Then
        assertThat(config.isActive()).isFalse();
        assertThat(config.getMappings()).isEmpty();
    }
}
