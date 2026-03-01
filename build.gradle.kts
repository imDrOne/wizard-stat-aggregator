import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.liquibase:liquibase-core:${property("liquibaseVersion")}")
        classpath("org.apache.commons:commons-lang3:${property("commonsLang3Version")}")
    }
}

plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.spring") version "2.1.21"
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.liquibase.gradle") version "3.1.0"
}

group = "xyz.candy-crawler"
version = "0.0.1-SNAPSHOT"
description = "wizard-stat-aggregator"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val dbProperties = Properties().apply {
    val file = rootProject.file("db.properties")
    if (file.exists()) load(file.inputStream())
}

fun dbProp(envKey: String, propKey: String) =
    System.getenv(envKey) ?: dbProperties.getProperty(propKey) ?: ""

val liquibaseVersion: String by project
val exposedVersion: String by project
val testContainersPostgresVersion: String by project

dependencies {
    // Core
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.jetbrains.exposed:exposed-spring-boot4-starter:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    runtimeOnly("org.postgresql:postgresql")

    liquibaseRuntime("org.liquibase:liquibase-core:$liquibaseVersion")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:4.0.1")
    liquibaseRuntime("info.picocli:picocli:4.6.1")
    liquibaseRuntime("org.postgresql:postgresql:42.7.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.boot:spring-boot-liquibase")
    testImplementation("org.testcontainers:testcontainers-postgresql:$testContainersPostgresVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.liquibase:liquibase-core:$liquibaseVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "logLevel" to "info",
            "changelogFile" to "src/main/resources/db/changelog/db.changelog-master.yaml",
            "url" to dbProp("DB_MIGRATION_URL", "db.migration.url"),
            "username" to dbProp("DB_MIGRATION_USERNAME", "db.migration.username"),
            "password" to dbProp("DB_MIGRATION_PASSWORD", "db.migration.password"),
            "driver" to "org.postgresql.Driver"
        )
    }
    runList = "main"
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register("createMigration") {
    group = "liquibase"
    description = "Creates a new Liquibase SQL migration file"

    doLast {
        val migrationName = project.findProperty("sqlName") as? String
            ?: error("Migration name is required. Use: ./gradlew createMigration -PsqlName=your_migration_name")

        val author = Runtime.getRuntime()
            .exec(arrayOf("git", "config", "user.name"))
            .inputStream.bufferedReader().readText().trim()
            .ifEmpty { "unknown" }

        val timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))

        val fileName = "${timestamp}_${migrationName}.sql"
        val migrationsDir = file("src/main/resources/db/changelog/migrations")
        val migrationFile = file("$migrationsDir/$fileName")

        migrationFile.writeText(
            """
            |-- liquibase formatted sql
            |
            |-- changeset $author:$timestamp
            |-- comment: $migrationName
            |
            |-- TODO: write your migration here
            |
            |-- rollback
            |-- TODO: write your rollback here
            """.trimMargin()
        )

        Runtime.getRuntime().exec(arrayOf("git", "add", "$migrationsDir"))

        println("✅ Migration created: src/main/resources/db/changelog/migrations/$fileName")
    }
}
