package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;

import static io.github.amiccoli.gherkindoctor.helper.RulesConfigurationHelper.mockRulesConfiguration;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockGherkinDoctorConfigurationHelper {

    public static GherkinDoctorConfiguration mockGherkinDoctorConfiguration() {
        var config = mock(GherkinDoctorConfiguration.class);
        given(config.getFeatureLocation()).willReturn("features");

        var rulesConfiguration = mockRulesConfiguration();
        given(config.getRules()).willReturn(rulesConfiguration);

        return config;
    }
}
