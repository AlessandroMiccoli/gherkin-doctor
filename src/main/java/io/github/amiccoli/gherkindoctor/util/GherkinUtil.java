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

package io.github.amiccoli.gherkindoctor.util;

import io.cucumber.messages.types.Feature;
import io.github.amiccoli.gherkindoctor.exception.MissingGherkinElementException;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GherkinUtil {

    /**
     * Ensures the presence of a Gherkin document URI, throwing an exception if absent.
     *
     * @param featureUri an {@link Optional} containing the feature URI
     * @return the feature URI if present
     * @throws MissingGherkinElementException if the feature URI is not present
     */
    public static String requireGherkinDocUri(Optional<String> featureUri) {
        return requirePresent(featureUri, "Feature URI is missing.");
    }

    /**
     * Ensures the presence of a Gherkin {@link Feature}, throwing an exception if absent.
     *
     * @param feature an {@link Optional} containing the Gherkin feature
     * @return the {@link Feature} if present
     * @throws MissingGherkinElementException if the feature is not present
     */
    public static Feature requireFeature(Optional<Feature> feature) {
        return requirePresent(feature, "Feature is missing.");
    }

    /**
     * Ensures the presence of a location column value, throwing an exception if absent.
     *
     * @param column an {@link Optional} containing the column value
     * @return the column value if present
     * @throws MissingGherkinElementException if the column value is not present
     */
    public static Long requireColumn(Optional<Long> column) {
        return requirePresent(column, "Location column is missing.");
    }

    private static <T> T requirePresent(Optional<T> optional, String errorMessage) {
        return optional.orElseThrow(() -> new MissingGherkinElementException(errorMessage));
    }
}
