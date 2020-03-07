import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Ver.kotlin
    kotlin("plugin.serialization") version Ver.kotlin
    id("com.github.johnrengelman.shadow") version "5.2.0"
    application
}

group = "io.github.meetlog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/excitement-engineer/ktor-graphql")
    maven("https://dl.bintray.com/kotlin/exposed")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(Ktor.server)
    implementation(Ktor.graphQl)
    implementation(Ktor.serialization)
    implementation(Ktor.jwt)
    implementation(Spring.crypto)
    implementation(KotlinX.serialization)
    implementation(Sql.exposed)
    implementation(Sql.hikariCp)
    implementation(Sql.mySql)
    implementation(Config.snakeYaml)

    testImplementation(Ktor.tests)
    testImplementation(kotlin("test-junit5"))
    testImplementation(Sql.h2)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName("jar").dependsOn("shadowJar")

application {
    mainClassName = "io.github.meetlog.server.ApplicationKt"
}