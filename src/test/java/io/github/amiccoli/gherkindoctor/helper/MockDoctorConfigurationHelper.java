package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.DoctorConfiguration;
import io.github.amiccoli.gherkindoctor.configuration.DoctorSetting;

import static io.github.amiccoli.gherkindoctor.helper.MockDoctorSettingHelper.mockDoctorSetting;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockDoctorConfigurationHelper {

    public static DoctorConfiguration mockDoctorConfiguration() {
        var doctorConfig = mock(DoctorConfiguration.class);
        var doctorSetting = mockDoctorSetting();

        given(doctorConfig.getDoctorSetting()).willReturn(doctorSetting);

        return doctorConfig;
    }

    public static DoctorConfiguration mockDoctorConfiguration(DoctorSetting doctorSetting) {
        var doctorConfig = mock(DoctorConfiguration.class);

        given(doctorConfig.getDoctorSetting()).willReturn(doctorSetting);

        return doctorConfig;
    }
}
