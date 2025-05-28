tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":core:core-enum"))
    implementation(project(":support:logging"))
    implementation(project(":storage:db-core"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    // implementation("org.springframework.batch:spring-batch-integration")

    testImplementation("org.springframework.batch:spring-batch-test")
}
