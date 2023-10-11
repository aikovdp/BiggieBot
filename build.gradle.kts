plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.10"
}

application.mainClass = "me.aikovdp.biggiebot.BiggieBotKt"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


repositories {
    mavenCentral()
    maven(url = "https://m2.dv8tion.net/releases")
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.15") {
        exclude(module = "opus-java")
    }
    implementation("com.github.minndevelopment:jda-ktx:9370cb13cc")
    implementation("ch.qos.logback:logback-classic:1.4.7")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}
