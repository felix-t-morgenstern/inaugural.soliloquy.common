/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/felix-t-morgenstern/inaugural.soliloquy.tools")
    }

    maven {
        url = uri("https://raw.githubusercontent.com/felix-t-morgenstern/soliloquy.artifacts/master/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api(libs.org.mockito.mockito.core)
    api(libs.com.google.inject.guice)
    api(libs.org.hamcrest.hamcrest.all)
    api(libs.disorg.soliloquy.soliloquy.specs)
    api(libs.disorg.soliloquy.inaugural.soliloquy.tools)
    testImplementation(libs.junit.junit)
}

group = "disorg.soliloquy"
version = "0.0.20"
description = "Inaugural Engine Common Component"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
