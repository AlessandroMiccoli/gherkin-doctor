package io.github.amiccoli.gherkindoctor.rule;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.factory.RuleFactory;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.amiccoli.gherkindoctor.helper.AnnotationTestHelper.getAnnotationForMethod;
import static io.github.amiccoli.gherkindoctor.helper.MockGherkinDoctorConfigurationHelper.mockGherkinDoctorConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RuleLoaderTest {

    GherkinDoctorConfiguration mockGherkinDoctorConfig;

    @BeforeEach
    void setUp() {
        mockGherkinDoctorConfig = mockGherkinDoctorConfiguration();
    }

    @Test
    void shouldReturnNoRulesWhenThereAreNoFactories() {
        // Given
        var ruleLoader = new RuleLoader(mockGherkinDoctorConfig, Collections.emptyList());

        // When
        var rules = ruleLoader.rules();

        // Then
        assertThat(rules).isEmpty();
    }

    @Test
    void shouldReturnTheRuleWhenThereIsAnActiveFactory() {
        // Given
        var mockFactory = mock(RuleFactory.class);
        var mockRule = mock(Rule.class);
        given(mockFactory.create(mockGherkinDoctorConfig)).willReturn(mockRule);

        var ruleLoader = new RuleLoader(mockGherkinDoctorConfig, List.of(mockFactory));

        // When
        var rules = ruleLoader.rules();

        // Then
        assertThat(rules)
                .hasSize(1)
                .containsExactly(mockRule);

        verify(mockFactory).create(mockGherkinDoctorConfig);
    }

    @Test
    void shouldHaveAssignedBeanAnnotationToRulesMethod() {
        // Given
        // When
        var beanAnnotation = getAnnotationForMethod(
                org.springframework.context.annotation.Bean.class,
                "rules",
                RuleLoader.class
        );

        // Then
        assertThat(beanAnnotation).isNotNull();
    }
}
