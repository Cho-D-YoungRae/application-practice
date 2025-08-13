rootProject.name = "spring-boot-kotlin-template"

include(
    "core:core-enum",
    "core:core-api",
    "batch",
    "worker",
    "storage:db-core",
    "client:client-example",
    "tests:api-docs",
    "support:logging",
    "support:monitoring"
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val asciidoctorConvertVersion: String by settings
    val ktlintVersion: String by settings
    val kspVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("org.asciidoctor.jvm.convert") version asciidoctorConvertVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
        id("com.google.devtools.ksp") version kspVersion
    }
}