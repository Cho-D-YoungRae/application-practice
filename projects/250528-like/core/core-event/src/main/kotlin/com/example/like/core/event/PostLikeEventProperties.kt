package com.example.like.core.event

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app.event.post-like")
data class PostLikeEventProperties(
    val topicName: String
)
