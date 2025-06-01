package com.example.like.core.domain

import java.time.LocalDateTime

data class Post(
    val id: PostId,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
)

data class PostId(val value: Long)