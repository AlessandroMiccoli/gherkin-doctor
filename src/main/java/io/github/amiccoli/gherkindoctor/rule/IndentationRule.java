package io.github.amiccoli.gherkindoctor.rule;

import io.cucumber.messages.types.Feature;
import io.cucumber.messages.types.FeatureChild;
import io.cucumber.messages.types.GherkinDocument;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.BACKGROUND;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.FEATURE;
import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.SCENARIO;
import static io.github.amiccoli.gherkindoctor.rule.RuleError.createForIndentationRule;
import static io.github.amiccoli.gherkindoctor.util.GherkinUtil.requireColumn;
import static io.github.amiccoli.gherkindoctor.util.GherkinUtil.requireFeature;
import static io.github.amiccoli.gherkindoctor.util.GherkinUtil.requireGherkinDocUri;

@Slf4j
@Getter
@AllArgsConstructor
public class IndentationRule implements Rule {

    private final EnumMap<GherkinElement, Long> constraints;

    @Override
    public List<RuleError> apply(GherkinDocument gherkinDocument) {
        val feature = requireFeature(gherkinDocument.getFeature());
        val docUri = requireGherkinDocUri(gherkinDocument.getUri());

        return constraints.keySet().stream()
                .flatMap(element -> switch (element) {
                    case FEATURE -> validateFeatureIndentation(feature, docUri);
                    case BACKGROUND -> feature.getChildren().stream()
                            .flatMap(featureChild -> validateBackgroundIndentation(featureChild, docUri));
                    case SCENARIO -> feature.getChildren().stream()
                            .flatMap(featureChild -> validateScenarioIndentation(featureChild, docUri));
                    default -> {
                        log.warn("Skip validation due to Gherkin element [{}] not currently handled for indentation.", element);
                        yield Stream.empty();
                    }
                })
                .toList();
    }

    private Stream<RuleError> validateFeatureIndentation(Feature feature, String docUri) {
        val featureColumn = requireColumn(feature.getLocation().getColumn());
        return featureColumn.equals(constraints.get(FEATURE))
                ? Stream.empty()
                : Stream.of(createForIndentationRule(
                        docUri,
                FEATURE,
                feature.getLocation().getLine(),
                featureColumn,
                constraints.get(FEATURE)));
    }

    private Stream<RuleError> validateBackgroundIndentation(FeatureChild featureChild, String docUri) {
        return featureChild.getBackground()
                .map(background -> {
                    val backgroundColumn = requireColumn(background.getLocation().getColumn());
                    return backgroundColumn.equals(constraints.get(BACKGROUND))
                            ? Stream.<RuleError>empty()
                            : Stream.of(createForIndentationRule(
                                    docUri,
                            BACKGROUND,
                            background.getLocation().getLine(),
                            backgroundColumn,
                            constraints.get(BACKGROUND))
                    );
                })
                .orElseGet(Stream::empty);
    }

    private Stream<RuleError> validateScenarioIndentation(FeatureChild featureChild, String docUri) {
        return featureChild.getScenario()
                .map(scenario -> {
                    val scenarioColumn = requireColumn(scenario.getLocation().getColumn());
                    return scenarioColumn.equals(constraints.get(SCENARIO))
                            ? Stream.<RuleError>empty()
                            : Stream.of(createForIndentationRule(
                            docUri,
                            SCENARIO,
                            scenario.getLocation().getLine(),
                            scenarioColumn,
                            constraints.get(SCENARIO))
                    );
                })
                .orElseGet(Stream::empty);
    }
}
