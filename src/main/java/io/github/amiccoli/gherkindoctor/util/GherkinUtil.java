package io.github.amiccoli.gherkindoctor.util;

import io.cucumber.messages.types.Feature;
import io.cucumber.messages.types.Scenario;
import io.github.amiccoli.gherkindoctor.exception.MissingGherkinElementException;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GherkinUtil {

    public static String requireGherkinDocUri(Optional<String> featureUri) {
        return requirePresent(featureUri, "Feature URI is missing.");
    }

    public static Feature requireFeature(Optional<Feature> feature) {
        return requirePresent(feature, "Feature is missing.");
    }

    public static Scenario requireScenario(Optional<Scenario> scenario) {
        return requirePresent(scenario, "Feature is missing.");
    }

    public static Long requireColumn(Optional<Long> column) {
        return requirePresent(column, "Location column is missing.");
    }

    private static <T> T requirePresent(Optional<T> optional, String errorMessage) {
        return optional.orElseThrow(() -> new MissingGherkinElementException(errorMessage));
    }
}
