package com.example.like.core.domain.v1

import com.example.like.storage.db.core.PostLikeJpaRepository
import org.springframework.stereotype.Component

@Component
class PostLikeRepository(
    private val likeJpaRepository: PostLikeJpaRepository
) {


}
