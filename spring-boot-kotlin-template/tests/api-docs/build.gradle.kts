dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework.boot:spring-boot-starter-test")
    api("io.rest-assured:spring-mock-mvc")
    api("org.springframework.restdocs:spring-restdocs-mockmvc")
    api("org.springframework.restdocs:spring-restdocs-restassured")
}
