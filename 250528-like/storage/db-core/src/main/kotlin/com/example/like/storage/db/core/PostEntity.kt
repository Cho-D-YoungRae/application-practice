package com.example.like.storage.db.core

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(
    name = "posts"
)
class PostEntity(
    @Column(name = "title", length = 255, nullable = false)
    val title: String,
    @Column(name = "content", length = 4000, nullable = false)
    val content: String,
): BaseEntity() {
}