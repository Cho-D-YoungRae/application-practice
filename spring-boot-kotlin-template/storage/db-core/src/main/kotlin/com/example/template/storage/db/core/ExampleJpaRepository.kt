package com.example.template.storage.db.core

import org.springframework.data.jpa.repository.JpaRepository

interface ExampleJpaRepository: JpaRepository<ExampleEntity, Long>