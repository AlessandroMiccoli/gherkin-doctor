package io.github.amiccoli.gherkindoctor.helper;

import io.github.amiccoli.gherkindoctor.configuration.DoctorSetting;

import static io.github.amiccoli.gherkindoctor.helper.MockRulesSettingHelper.mockRulesSetting;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockDoctorSettingHelper {

    public static DoctorSetting mockDoctorSetting() {
        var doctorSetting = mock(DoctorSetting.class);
        given(doctorSetting.getFeatureLocation()).willReturn("features");

        var mockRulesSetting = mockRulesSetting();
        given(doctorSetting.getRulesSetting()).willReturn(mockRulesSetting);

        return doctorSetting;
    }
}
