package com.example.like.core.domain.v1

import com.example.like.core.domain.PostLike
import com.example.like.storage.db.core.PostLikeEntity
import com.example.like.storage.db.core.PostLikeJpaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class PostLikeRepository(
    private val postLikeJpaRepository: PostLikeJpaRepository
) {

    @Transactional
    fun like(postLike: PostLike): Boolean {
        return postLikeJpaRepository.findByPostIdAndUserId(
            postLike.postId.value,
            postLike.userId.value
        )?.let { entity ->
            if (entity.isDeleted()) {
                entity.restore()
                true
            } else {
                false
            }
        } ?: run {
            postLikeJpaRepository.save(
                PostLikeEntity(postLike.postId.value, postLike.userId.value)
            )
            true
        }
    }

}
