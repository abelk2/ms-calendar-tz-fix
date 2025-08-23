import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

plugins {
    kotlin("jvm") version "2.0.20"
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "eu.abelk.tzfix"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:3.1.2")
    implementation("io.ktor:ktor-server-netty-jvm:3.1.2")
    implementation("io.ktor:ktor-client-core:3.1.2")
    implementation("io.ktor:ktor-client-cio-jvm:3.1.2")
    implementation("io.ktor:ktor-server-call-logging:3.1.2")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

jib {
    from {
        image = "eclipse-temurin:17.0.15_6-jre-jammy"
        platforms {
            platform {
                architecture = "arm"
                os = "linux"
            }
        }
    }
    to {
        image = "abelk/ms-calendar-tz-fix:${project.version}"
    }
    container {
        mainClass = "eu.abelk.tzfix.MainKt"
        ports = listOf("8080")
        creationTime = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)
    }
    setAllowInsecureRegistries(true)
}
