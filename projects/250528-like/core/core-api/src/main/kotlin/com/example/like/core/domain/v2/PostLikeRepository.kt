package com.example.like.core.domain.v2

import com.example.like.core.domain.PostId
import com.example.like.core.domain.PostLike
import com.example.like.core.domain.PostLikeCount
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

    fun getCounts(postIds: List<PostId>): List<PostLikeCount> {
        return postLikeJpaRepository.countByPostIdInAndGroupByPostId(postIds.map { it.value })
            .map { PostLikeCount(postId = PostId(it.postId), count = it.likeCount.toInt()) }
    }

}
