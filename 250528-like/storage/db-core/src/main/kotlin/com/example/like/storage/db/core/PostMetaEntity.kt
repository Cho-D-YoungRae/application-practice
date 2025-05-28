package com.example.like.storage.db.core

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "post_meta",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_post_meta_1", columnNames = ["post_id"])
    ],
    indexes = [
        Index(name = "ix_post_meta_1", columnList = "like_count")
    ]
)
class PostMetaEntity(
    @Column(name = "post_id", nullable = false)
    val postId: Long,
    @Column(name = "like_count", nullable = false)
    var likeCount: Int = 0,
) : BaseEntity() {
}