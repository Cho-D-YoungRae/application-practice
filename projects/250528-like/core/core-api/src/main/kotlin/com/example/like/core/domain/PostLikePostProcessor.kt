package com.example.like.core.domain

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PostLikePostProcessor(
    private val postLikeRepository: PostLikeRepository,
) {
    @Async
    fun countUp(postLike: PostLike) {
        postLikeRepository.countUp(postLike)
    }
}
