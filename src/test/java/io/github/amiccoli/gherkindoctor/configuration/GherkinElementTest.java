package io.github.amiccoli.gherkindoctor.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GherkinElementTest {

    @Test
    void shouldHaveDefinedRuleTypeElements() {
        // Given
        // When
        // Then
        assertThat(GherkinElement.values()).hasSize(19);
    }
}
