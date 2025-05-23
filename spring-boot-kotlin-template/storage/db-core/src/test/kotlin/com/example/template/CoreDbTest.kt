package com.example.template

import com.example.template.storage.db.core.CoreDbContextTest
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@SpringBootApplication
class CoreDbTest: CoreDbContextTest() {

    @Test
    fun contextLoads() {
    }
}