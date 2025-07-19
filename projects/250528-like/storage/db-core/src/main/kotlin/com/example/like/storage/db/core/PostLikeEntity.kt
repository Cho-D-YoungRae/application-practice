package com.example.like.storage.db.core

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "post_like",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_post_like__1", columnNames = ["post_id", "user_id"]),
    ],
    indexes = [
        Index(name = "ix_post_like__1", columnList = "user_id"),
    ],
)
class PostLikeEntity(
    @Column(name = "post_id", nullable = false)
    val postId: Long,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
) : BaseEntity()
