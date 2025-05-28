package com.example.like.storage.db.core

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime? = null

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false

    fun delete() {
        deleted = true
    }

    fun restore() {
        deleted = false
    }

    fun isDeleted() = deleted

    fun isActive() = !deleted
}