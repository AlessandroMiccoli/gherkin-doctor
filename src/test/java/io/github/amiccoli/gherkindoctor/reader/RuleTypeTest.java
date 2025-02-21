package io.github.amiccoli.gherkindoctor.reader;

import io.github.amiccoli.gherkindoctor.rule.RuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RuleTypeTest {

    @Test
    void shouldHaveDefinedRuleTypeElements() {
        // Given
        // When
        // Then
        assertThat(RuleType.values()).hasSize(1);
    }
}
