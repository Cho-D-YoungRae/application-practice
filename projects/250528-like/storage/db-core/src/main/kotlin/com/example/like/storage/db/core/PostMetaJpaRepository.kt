package com.example.like.storage.db.core

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository

@Repository
interface PostMetaJpaRepository : JpaRepository<PostMetaEntity, Long>, PostMetaJpaRepositoryCustom {

    fun findAllByPostIdIn(postIds: Collection<Long>): List<PostMetaEntity>

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    fun findWithOptimisticLockByPostId(postId: Long): PostMetaEntity?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllWithPessimisticLockByPostIdIn(postIds: Collection<Long>): List<PostMetaEntity>
}
