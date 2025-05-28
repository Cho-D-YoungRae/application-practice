package com.example.like.storage.db.core.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class CoreDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "storage.datasource.core")
    fun coreDataSource(): HikariDataSource? {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }
}