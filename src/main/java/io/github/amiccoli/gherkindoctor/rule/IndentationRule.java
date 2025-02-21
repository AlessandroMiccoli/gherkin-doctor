package io.github.amiccoli.gherkindoctor.rule;

import io.cucumber.messages.types.Feature;
import io.cucumber.messages.types.FeatureChild;
import io.cucumber.messages.types.GherkinDocument;
import io.github.amiccoli.gherkindoctor.configuration.GherkinDoctorConfiguration;
import io.github.amiccoli.gherkindoctor.configuration.GherkinElement;
import java.util.*;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import static io.github.amiccoli.gherkindoctor.configuration.GherkinElement.*;
import static io.github.amiccoli.gherkindoctor.rule.RuleError.createForIndentationRule;
import static io.github.amiccoli.gherkindoctor.util.GherkinUtil.*;

@Slf4j
@Getter
@Component
public class IndentationRule implements Rule {

    EnumMap<GherkinElement, Long> indentationConstraints;

    @Override
    public void setConstraint(GherkinDoctorConfiguration gherkinDoctorConfiguration) {
        indentationConstraints = gherkinDoctorConfiguration.getRules().getIndentation().getMappings();
    }

    @Override
    public List<RuleError> apply(GherkinDocument gherkinDocument) {
        val feature = requireFeature(gherkinDocument.getFeature());
        val docUri = requireGherkinDocUri(gherkinDocument.getUri());

        return indentationConstraints.keySet().stream()
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
        return featureColumn.equals(indentationConstraints.get(FEATURE))
                ? Stream.empty()
                : Stream.of(createForIndentationRule(
                        docUri,
                FEATURE,
                featureColumn,
                indentationConstraints.get(FEATURE))
        );
    }

    private Stream<RuleError> validateBackgroundIndentation(FeatureChild featureChild, String docUri) {
        return featureChild.getBackground()
                .map(background -> {
                    val backgroundColumn = requireColumn(background.getLocation().getColumn());
                    return backgroundColumn.equals(indentationConstraints.get(BACKGROUND))
                            ? Stream.<RuleError>empty()
                            : Stream.of(createForIndentationRule(
                                    docUri,
                            BACKGROUND,
                            backgroundColumn,
                            indentationConstraints.get(BACKGROUND))
                    );
                })
                .orElseGet(Stream::empty);
    }

    private Stream<RuleError> validateScenarioIndentation(FeatureChild featureChild, String docUri) {
        val scenario = requireScenario(featureChild.getScenario());
        val scenarioColumn = requireColumn(scenario.getLocation().getColumn());
        return scenarioColumn.equals(indentationConstraints.get(SCENARIO))
                ? Stream.empty()
                : Stream.of(createForIndentationRule(
                        docUri,
                SCENARIO,
                scenarioColumn,
                indentationConstraints.get(SCENARIO))
        );
    }
}
