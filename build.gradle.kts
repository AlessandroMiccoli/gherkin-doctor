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

    /*=============*/
    /* Spring Boot */
    /*=============*/

    // https://plugins.gradle.org/plugin/org.springframework.boot
    id("org.springframework.boot") version "3.4.2"

    // https://plugins.gradle.org/plugin/io.spring.dependency-management
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.amiccoli"
version = System.getenv("LIBRARY_VERSION") ?: "1.0.0"

repositories {
    mavenCentral()
}

val logbackVersion = "7.4"
val springCloudVersion = "2024.0.0"
val springShellVersion = "3.4.0"
val cucumberGherkinVersion = "31.0.0"
val cucumberMessagesVersion = "27.2.0"
val jUnitVersion = "1.11.4"
val mockitoVersion = "5.2.0"

dependencyManagement {
    imports {
        // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        mavenBom("org.springframework.shell:spring-shell-dependencies:${springShellVersion}")
    }
}

dependencies {
    /*=============*/
    /* Spring Boot */
    /*=============*/
    implementation("org.springframework.boot:spring-boot-starter-web")

    //noinspection GradlePackageUpdate
    implementation("org.springframework.boot:spring-boot-starter")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    //noinspection GradlePackageUpdate
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // Exclusions
        exclude(group = "org.hamcrest", module = "hamcrest")
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.shell:spring-shell-starter")

    /*=========*/
    /* Logging */
    /*=========*/
    // https://mvnrepository.com/artifact/net.logstash.logback/logstash-logback-encoder
    implementation("org.springframework.boot:spring-boot-starter-logging")

    implementation("net.logstash.logback:logstash-logback-encoder:${logbackVersion}")

    /*====================*/
    /* BDD Test Framework */
    /*====================*/
    // https://mvnrepository.com/artifact/io.cucumber/gherkin
    implementation("io.cucumber:gherkin:${cucumberGherkinVersion}")

    // https://mvnrepository.com/artifact/io.cucumber/messages
    implementation("io.cucumber:messages:${cucumberMessagesVersion}")

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-suite
    implementation("org.junit.platform:junit-platform-suite:${jUnitVersion}")

    // https://mvnrepository.com/artifact/org.mockito/mockito-inline
    testImplementation("org.mockito:mockito-inline:${mockitoVersion}")
}

tasks.jar {
    enabled = true
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.bootJar {
    enabled = false
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

apply(from = "gradle/scripts/test.gradle")
