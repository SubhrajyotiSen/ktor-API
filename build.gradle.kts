import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "me.subhrajyoti"
version = "1.0"

val ktor_version = "1.0.0-beta-3"

plugins {
    application
    kotlin("jvm") version "1.3.0"
}

kotlin.experimental.coroutines = Coroutines.ENABLE

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.ktor:ktor-jackson:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
    testCompile(group = "junit", name = "junit", version = "4.12")
}
