tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":core:core-event"))
    implementation(project(":storage:db-core"))
    implementation(project(":support:logging"))
    implementation(project(":support:monitoring"))

    testImplementation("org.springframework.kafka:spring-kafka-test")
}
