package com.example.like.core.api.controller.response

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val likeCount: Int,
    val createdAt: LocalDateTime,
)
