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
    maven (url = "https://m2.dv8tion.net/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.15")
    implementation("ch.qos.logback:logback-classic:1.4.7")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}
