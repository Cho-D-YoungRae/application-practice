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
    private val optimisticLockTemplate: OptimisticLockTemplate,
) {
    @Transactional
    fun like(postLike: PostLike): Boolean {
        return doLike(postLike)
    }

    fun likeWithMeta(postLike: PostLike): Boolean {
        return optimisticLockTemplate.execute {
            doLike(postLike).also { if (it) doCountUp(postLike) }
        }
    }

    @Transactional
    fun countUp(postLike: PostLike) {
        doCountUp(postLike)
    }

    private fun doCountUp(postLike: PostLike) {
        postMetaJpaRepository.findWithOptimisticLockByPostId(postLike.postId.value)!!.likeUp()
    }

    private fun doLike(postLike: PostLike): Boolean =
        postLikeJpaRepository.findByPostIdAndUserId(
            postLike.postId.value,
            postLike.userId.value,
        )?.let { _ ->
            false
        } ?: run {
            postLikeJpaRepository.save(
                PostLikeEntity(
                    postLike.postId.value,
                    postLike.userId.value,
                ),
            )
            true
        }

    fun getCounts(postIds: List<PostId>): List<PostLikeCount> {
        return postLikeJpaRepository.countByPostIdInAndGroupByPostId(postIds.map { it.value })
            .map { PostLikeCount(postId = PostId(it.postId), count = it.likeCount.toInt()) }
    }

    fun getCountsWithMeta(postIds: List<PostId>): List<PostLikeCount> {
        return postMetaJpaRepository.findAllByPostIdIn(postIds.map { it.value })
            .map { PostLikeCount(postId = PostId(it.postId), count = it.likeCount) }
    }
}
