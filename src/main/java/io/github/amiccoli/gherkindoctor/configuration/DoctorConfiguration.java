/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.amiccoli.gherkindoctor.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.amiccoli.gherkindoctor.exception.ConfigurationException;
import io.github.amiccoli.gherkindoctor.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class DoctorConfiguration {

    private static final String DOCTOR_CONFIG_FILE = "gherkin-doctor.yaml";
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @JsonProperty("gherkin-doctor")
    private DoctorSetting doctorSetting;

    /**
     * Deserializes the DoctorConfiguration from the input stream and validate the DoctorSetting.
     *
     * @return the {@link DoctorSetting} object.
     */
    public static DoctorSetting loadDoctorSetting() {
        try (InputStream inputStream = FileUtil.getInputStream(DOCTOR_CONFIG_FILE)) {
            val configuration = deserializeConfiguration(inputStream);

            validateDoctorSetting(configuration.getDoctorSetting());

            return configuration.getDoctorSetting();
        } catch (JsonProcessingException jsonProcessingException) {
            throw new ConfigurationException("Invalid YAML format in [%s]: %s.".formatted(
                    DOCTOR_CONFIG_FILE,
                    jsonProcessingException.getMessage()
            ), jsonProcessingException);
        } catch (IOException ioException) {
            throw new ConfigurationException("Error reading YAML configuration from [%s]: %s.".formatted(
                    DOCTOR_CONFIG_FILE,
                    ioException.getMessage()
            ), ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ConfigurationException("Invalid file: %s.".formatted(
                    illegalArgumentException.getMessage()
            ), illegalArgumentException);
        }
    }

    private static DoctorConfiguration deserializeConfiguration(InputStream inputStream) throws IOException {
        return MAPPER.readValue(inputStream, DoctorConfiguration.class);
    }

    private static void validateDoctorSetting(DoctorSetting doctorSetting) {
        if (doctorSetting == null) {
            throw new ConfigurationException("Doctor Setting is missing or invalid.");
        }

        doctorSetting.validate();
    }
}
