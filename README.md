# Gherkin Doctor - Your trustworthy toolkit for feature file

**gherkin-doctor** is a toolkit for analyzing and improving feature files written in Gherkin syntax. While it currently provides a linter to enforce best practices through configurable rules, its modular design allows for future extensions. The rule loading system is built using Spring Boot, following the Factory & Strategy Pattern for flexibility and scalability.

## Table of Contents

- [Design](#design)
- [Configuration](#configuration)
- [Usage](#usage)
- [Rules](#rules)
- [How to Extend](#how-to-extend)
- [Contributing](#contributing)
- [License](#license)

## Design

The rule loading mechanism consists of:

- GherkinDoctorConfiguration: Loads constraints from a YAML configuration file.
- RuleFactory: Define the creation of the rule.
- Rule: Defines the behaviour of the rule.
- RuleLoader: Automatically collects and provides rule strategies using Spring Boot’s dependency injection.
- GherkinDoctorToolkit: Provide the available features.

## Configuration

Configuration for **gherkin-doctor** is managed via YAML files. The rules can be defined and customised through a configuration file (`application.yaml`).

Each rule requires you to define its activation status and the specific [Gherkin Element](src/main/java/io/github/amiccoli/gherkindoctor/configuration/GherkinElement.java) it targets.

The [GherkinDoctorConfiguration](src/main/java/io/github/amiccoli/gherkindoctor/configuration/GherkinDoctorConfiguration.java) class validates the rule before it is applied to the Gherkin documents.

Example of a basic YAML configuration rule:

```yaml
rules:
  indentation:
    active: true
    mappings:
      feature: 0
      background: 2
      scenario: 2
      step: 4
```

## Usage

**gherkin-doctor** is currently hard at work diagnosing your Gherkin feature files. Temporarily, to use it, simply run the project and execute the following command:

```bash
gherkin-doctor lint
```

## Rules
**gherkin-doctor** currently integrate the following rule:

- Indentation

## How to extend

**gherkin-doctor** follows the Factory & Strategy Pattern for flexibility and scalability. For adding additional rules, you need:

- Defines a new rule. First check if it is not already integrated.
- Adds the rule in the `application.yml` behind `rules`. 
- Handle the reading of the configured rule in [RulesConfiguration](src/main/java/io/github/amiccoli/gherkindoctor/configuration/RulesConfiguration.java).
- Implements the [RuleFactory](src/main/java/io/github/amiccoli/gherkindoctor/factory/RuleFactory.java). Remember to annotate it with `@Component`.
- Implements a new [Rule](src/main/java/io/github/amiccoli/gherkindoctor/rule/Rule.java) strategy for the new rule.
- The new rule strategy will be automatically registered in the [RuleLoader](src/main/java/io/github/amiccoli/gherkindoctor/rule/RuleLoader.java).
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