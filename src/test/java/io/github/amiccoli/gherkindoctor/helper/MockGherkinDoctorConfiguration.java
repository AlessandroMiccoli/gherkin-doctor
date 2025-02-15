package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockGherkinDoctorConfiguration {

    public static GherkinDoctorConfiguration mockGherkinDoctorConfiguration() {
        var config = mock(GherkinDoctorConfiguration.class);
        given(config.getFeatureLocation()).willReturn("features");

        return config;
    }
}
