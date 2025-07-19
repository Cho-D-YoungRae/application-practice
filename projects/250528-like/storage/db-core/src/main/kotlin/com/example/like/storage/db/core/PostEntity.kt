package com.example.like.storage.db.core

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "posts",
    indexes = [
        Index(name = "ix_posts__1", columnList = "created_at desc"),
    ],
)
class PostEntity(
    @Column(name = "title", length = 255, nullable = false)
    val title: String,
    @Column(name = "content", length = 4000, nullable = false)
    val content: String,
) : BaseEntity()
