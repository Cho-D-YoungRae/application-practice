package com.example.like.storage.db.core

import com.example.like.storage.db.core.QPostEntity.postEntity
import com.example.like.storage.db.core.QPostLikeEntity.postLikeEntity
import com.example.template.core.enums.ListOrder
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager

class PostLikeJpaRepositoryCustomImpl(
    entityManager: EntityManager
): PostLikeJpaRepositoryCustom {

    private val queryFactory = JPAQueryFactory(entityManager)

    override fun countByPostIdInAndGroupByPostId(postIds: Collection<Long>): List<PostLikeCountProjection> {
        return queryFactory
            .select(QPostLikeCountProjection(
                postLikeEntity.postId,
                postLikeEntity.postId.count()
            ))
            .from(postLikeEntity)
            .where(postLikeEntity.postId.`in`(postIds))
            .groupBy(postLikeEntity.postId)
            .fetch()
    }

    override fun findPostIdsOrderByLikeCount(page: Int, size: Int, order: ListOrder): List<Long> {
        return queryFactory.select(postLikeEntity.postId)
            .from(postLikeEntity)
            .join(postEntity).on(postEntity.id.eq(postLikeEntity.postId))
            .where(postEntity.deleted.isFalse)
            .groupBy(postLikeEntity.postId)
            .orderBy(postLikeEntity.postId.count().toOrder(order))
            .offset((page * size).toLong())
            .limit(size.toLong())
            .fetch()
    }
}