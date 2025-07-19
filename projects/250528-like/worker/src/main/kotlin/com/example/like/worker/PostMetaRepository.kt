package com.example.like.worker

import com.example.like.storage.db.core.PostMetaJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PostMetaRepository(
    private val postMetaJpaRepository: PostMetaJpaRepository,
) {
    @Transactional
    fun likeCountUp(postIds: List<Long>) {
        val postIdToCount = postIds.groupingBy { it }.eachCount()
        val postMetaEntities = postMetaJpaRepository.findAllByPostIdIn(postIdToCount.keys)
        postMetaEntities.forEach { postMetaEntity ->
            repeat(postIdToCount[postMetaEntity.id] ?: 0) {
                postMetaEntity.likeUp()
            }
        }
    }
}
