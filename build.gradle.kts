import org.jetbrains.intellij.tasks.PublishTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// https://kotlinlang.org/docs/reference/using-gradle.html
object Versions {
    const val spring = "5.3.3"
}

plugins {
    idea
    jacoco // https://docs.gradle.org/current/userguide/jacoco_plugin.html
    id("org.jetbrains.intellij") version "0.4.21" // https://github.com/JetBrains/gradle-intellij-plugin
    kotlin("jvm") version "1.3.70"
    id("org.sonarqube") version "2.8"
}

sonarqube {
    properties {
        property("sonar.projectKey", "masooh_groovyfier")
        property("sonar.organization", "masooh")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.login", "a33bf2e7694238965372c69a490d0b5c7ce56b58")
    }
}

intellij {
    pluginName = "JUnit to Spock Converter"
    version = "2019.3.3" // overrides plugin.xml since-build in case of conflict, https://www.jetbrains.com/intellij-repository/releases
    // transitive dependencies has to added: https://github.com/JetBrains/gradle-intellij-plugin/issues/38
    setPlugins("Groovy", "java", "properties") // Bundled plugin dependencies
    updateSinceUntilBuild = false
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
    }
}

tasks.withType<PublishTask> {
    if (project.hasProperty("intellijPublishToken")) {
        token(project.property("intellijPublishToken"))
    }
}

group = "com.github.masooh.intellij.plugin.junitspock"
version = "0.2" // overrides plugin.xml version in case of conflict

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk7"))
    implementation(kotlin("stdlib-jdk8"))

    /*
       the following test dependencies break test execution
       but are helpful for code completion and syntax check in src/test/resources/testdata
       Test classpath is defined by Test#getProjectDescriptor()
     */
//    testImplementation("org.springframework:spring-context:${Versions.spring}")
//    testImplementation("org.springframework:spring-test:${Versions.spring}")
//    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
//    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}