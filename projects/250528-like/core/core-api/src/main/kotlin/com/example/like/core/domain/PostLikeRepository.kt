package com.example.like.core.domain

import com.example.like.storage.db.core.PostLikeEntity
import com.example.like.storage.db.core.PostLikeJpaRepository
import com.example.like.storage.db.core.PostMetaJpaRepository
import com.example.like.storage.db.core.lock.OptimisticLockTemplate
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class PostLikeRepository(
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val postMetaJpaRepository: PostMetaJpaRepository,
    private val optimisticLockTemplate: OptimisticLockTemplate
) {

    @Transactional
    fun likeWithoutMeta(postLike: PostLike): Boolean {
        return like(postLike)
    }

    fun likeWithMeta(postLike: PostLike): Boolean {
        return optimisticLockTemplate.execute<Boolean> {
            val likeResult = like(postLike)
            if (likeResult) {
                postMetaJpaRepository.findByPostId(postLike.postId.value)!!.likeUp()
            }
            likeResult
        }
    }

    private fun like(postLike: PostLike): Boolean = postLikeJpaRepository.findByPostIdAndUserId(
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

    fun getCountsWithoutMeta(postIds: List<PostId>): List<PostLikeCount> {
        return postLikeJpaRepository.countByPostIdInAndGroupByPostId(postIds.map { it.value })
            .map { PostLikeCount(postId = PostId(it.postId), count = it.likeCount.toInt()) }
    }

    fun getCountsWithMeta(postIds: List<PostId>): List<PostLikeCount> {
        return postMetaJpaRepository.findAllByPostIdIn(postIds.map { it.value })
            .map { PostLikeCount(postId = PostId(it.postId), count = it.likeCount) }
    }

}
