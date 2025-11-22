import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.openjfx.javafxplugin") version "0.1.0"
    application
}

group = "com.app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.11.0"

// Java 21
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

// Kotlin compilation
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "19"
}

// Application plugin
application {
    mainModule.set("com.app.main")
    mainClass.set("com.app.main.Game")
}

// JavaFX modules
javafx {
    version = "23"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.web",
        "javafx.media"
    )
}

// Dépendances
dependencies {
    implementation(kotlin("stdlib"))

    // Tests JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

// Configuration tests
tasks.withType<Test> {
    useJUnitPlatform()
}
