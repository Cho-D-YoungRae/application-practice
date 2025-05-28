package com.example.like.storage.db.core

import jakarta.persistence.*

@Entity
@Table(
    name = "post_like",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_post_like__1", columnNames = ["user_id", "post_id"])
    ],
    indexes = [
        Index(name = "ix_post_like__1", columnList = "user_id")
    ]
)
class PostLikeEntity(
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "post_id", nullable = false)
    val postId: Long
): BaseEntity() {
}