package io.github.amiccoli.gherkindoctor.configuration;

import java.util.EnumMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseRuleConfiguration<T> {
    boolean active = false;
    EnumMap<GherkinElement, T> mappings = new EnumMap<>(GherkinElement.class);
}
