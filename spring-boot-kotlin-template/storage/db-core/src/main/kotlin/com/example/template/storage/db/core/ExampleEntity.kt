package com.example.template.storage.db.core

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "example")
class ExampleEntity(
    @Column(name = "val")
    val value: String
): BaseEntity()