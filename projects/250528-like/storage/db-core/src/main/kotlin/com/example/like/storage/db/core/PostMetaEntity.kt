package com.example.like.storage.db.core

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Version
import kotlin.math.max

@Entity
@Table(
    name = "post_meta",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_post_meta__1", columnNames = ["post_id"]),
    ],
    indexes = [
        Index(name = "ix_post_meta__1", columnList = "deleted, like_count, post_id"),
    ],
)
class PostMetaEntity(
    @Column(name = "post_id", nullable = false)
    val postId: Long,
) : BaseEntity() {
    @Column(name = "like_count", nullable = false)
    var likeCount: Int = 0

    @Version
    @Column(name = "version", nullable = false)
    private val version = 0

    fun likeUp() {
        likeCount += 1
    }

    fun likeDown() {
        likeCount = max(likeCount - 1, 0)
    }

    fun update(likeCount: Int) {
        this.likeCount = max(likeCount, 0)
    }
}
