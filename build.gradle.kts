plugins {
    `java-library`

    // For use with IntelliJ
    idea

    /*=================*/
    /* Static Analysis */
    /*=================*/
    jacoco

    // https://plugins.gradle.org/plugin/org.sonarqube
    id("org.sonarqube") version "6.0.1.5171" apply false
}

group = "io.github.amiccoli"
version = System.getenv("LIBRARY_VERSION") ?: "1.0.0"

repositories {
    mavenCentral()
}

val logbackVersion = "1.5.17"
val slf4jVersion = "2.0.17"
val cucumberGherkinVersion = "31.0.0"
val cucumberMessagesVersion = "27.2.0"
val jUnitVersion = "1.11.4"
val mockitoVersion = "5.2.0"
val junitJupiterVersion = "5.12.0"
val assertjVersion = "3.27.3"
val jacksonYamlVersion = "2.18.3"
val lombokVersion = "1.18.36"


dependencies {
    /*====================*/
    /* BDD Test Framework */
    /*====================*/

    // https://mvnrepository.com/artifact/io.cucumber/gherkin
    implementation("io.cucumber:gherkin:${cucumberGherkinVersion}")

    // https://mvnrepository.com/artifact/io.cucumber/messages
    implementation("io.cucumber:messages:${cucumberMessagesVersion}")

    /*=======*/
    /* JUnit */
    /*=======*/

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-suite
    testImplementation("org.junit.platform:junit-platform-suite:${jUnitVersion}")

    // https://mvnrepository.com/artifact/org.mockito/mockito-inline
    testImplementation("org.mockito:mockito-inline:${mockitoVersion}")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter:${junitJupiterVersion}")

    // https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:$assertjVersion")

    /*=========*/
    /* Logging */
    /*=========*/

    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation("ch.qos.logback:logback-classic:${logbackVersion}")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")

    /*=========*/
    /* Utility */
    /*=========*/

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jacksonYamlVersion}")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    implementation("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

apply(from = "gradle/scripts/test.gradle")
