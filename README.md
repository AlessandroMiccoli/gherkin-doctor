# Gherkin Doctor - Your trustworthy toolkit for feature file

**gherkin-doctor** is a toolkit for analyzing and improving feature files written in Gherkin syntax. While it currently provides a linter to enforce best practices through configurable ruleStrategies, its modular design allows for future extensions. The rule loading system is built using Java, following the Factory & Strategy Pattern for flexibility and scalability.

## Table of Contents

- [Design](#design)
- [Configuration](#configuration)
- [Usage](#usage)
- [Rules](#ruleStrategies)
- [How to Extend](#how-to-extend)
- [Contributing](#contributing)
- [License](#license)

## Design

The rule loading mechanism consists of:

- DoctorConfiguration: Loads constraints from a YAML configuration file.
- RuleFactory: Define the creation of the rule.
- RuleStrategy: Defines the behaviour of the rule.
- RuleLoader: Create rule strategies.
- DoctorToolkit: Provide the available features: `lint`.

## Configuration

Configuration for **gherkin-doctor** is managed via YAML files. The rule strategies can be defined and customised through a dedicated file (`gherkin-doctor.yaml`).

Each rule requires you to define its activation status and the specific [Gherkin Element](src/main/java/io/github/amiccoli/gherkindoctor/configuration/GherkinElement.java) it targets.

The [DoctorConfiguration](src/main/java/io/github/amiccoli/gherkindoctor/configuration/DoctorConfiguration.java) class validates the rule before it is applied to the Gherkin documents.

Example of a basic YAML configuration rule:

```yaml
gherkin-doctor:
  feature-location: "features"
  rules:
    indentation:
      active: true
      mappings:
        feature: 1
        background: 3
        scenario: 3
        step: 5
```

## Usage

**gherkin-doctor** is currently hard at work diagnosing your Gherkin feature files. Temporarily, to use it, simply run the main method in [MainApplication](src/main/java/io/github/amiccoli/gherkindoctor/MainApplication.java)

## Rules
**gherkin-doctor** currently integrate the following rule:

- Indentation

## How to extend

**gherkin-doctor** follows the Factory & Strategy Pattern for flexibility and scalability. For adding additional ruleStrategies, you need:

- Defines a new rule. First check if it is not already integrated.
- Adds the rule in the `gherkin-doctor.yml` behind `rules`. 
- Handle the reading of the configured rule in [RulesSetting](src/main/java/io/github/amiccoli/gherkindoctor/configuration/RulesSetting.java).
- Implements the [RuleFactory](src/main/java/io/github/amiccoli/gherkindoctor/factory/RuleFactory.java).
- Implements a new [RuleStrategy](src/main/java/io/github/amiccoli/gherkindoctor/rule/RuleStrategy.java) strategy for the new rule.
- The [RuleLoader](src/main/java/io/github/amiccoli/gherkindoctor/rule/RuleLoader.java) will create all the rules.
- The new rule will apply in the toolkit functionalities, such as `lint`.

## Future Enhancements

- More rule categories (e.g., case, line length, duplication detection, etc...).
- Supports custom rule definitions within projects that use gherkin-doctor.
- Additional functionality in the toolkit, such as `prettier`, `stats`.
- Integration as a plugin.
  
## Contributing

Contributions are welcome! As a general rule: less complains, more patches.

Rules for contributions will come soon.

## License
gherkin-doctor is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.

### Attribution Requirement
If you use or distribute this project or its derivative works, you **must** include the following attribution:

> **"This software is based on Gherkin-Doctor by Alessandro Miccoli (https://github.com/AlessandroMiccoli/gherkin-doctor)."**

Failure to provide attribution may result in a violation of the intended licensing terms.