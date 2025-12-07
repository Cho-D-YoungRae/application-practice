dependencies {
    implementation(platform("io.sentry:sentry-bom:${property("sentryVersion")}"))
    implementation("io.sentry:sentry-spring-boot-4-starter")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
}
