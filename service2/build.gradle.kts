plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "com.nodalx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.rabbitmq:amqp-client:5.20.0") // RabbitMQ client
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0") // For async tasks
    implementation("ch.qos.logback:logback-classic:1.2.12") // Logback for logging (Java 8 compatible)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.nodalx.s2.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.nodalx.s2.MainKt"
    }
    archiveFileName.set("service2.jar")
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE // Exclude duplicate files
    }
}