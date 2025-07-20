package com.example.like.storage.db.core

import com.example.like.core.enums.ListOrder
import com.example.like.storage.db.core.QPostEntity.postEntity
import com.example.like.storage.db.core.QPostLikeEntity.postLikeEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager

class PostLikeJpaRepositoryCustomImpl(
    entityManager: EntityManager,
) : PostLikeJpaRepositoryCustom {
    private val queryFactory = JPAQueryFactory(entityManager)

    override fun countByPostIdInAndGroupByPostId(postIds: Collection<Long>): List<PostLikeCountProjection> {
        return queryFactory
            .select(
                QPostLikeCountProjection(
                    postLikeEntity.postId,
                    postLikeEntity.postId.count(),
                ),
            )
            .from(postLikeEntity)
            .where(postLikeEntity.postId.`in`(postIds))
            .groupBy(postLikeEntity.postId)
            .fetch()
    }

    override fun findCountsOrderByLikeCount(
        page: Int,
        size: Int,
        order: ListOrder,
    ): List<PostLikeCountProjection> {
        return queryFactory
            .select(QPostLikeCountProjection(
                postLikeEntity.postId,
                postLikeEntity.postId.count(),
            ))
            .from(postLikeEntity)
            .join(postEntity).on(postEntity.id.eq(postLikeEntity.postId))
            .groupBy(postLikeEntity.postId)
            .orderBy(postLikeEntity.postId.count().toOrder(order))
            .offset((page * size).toLong())
            .limit(size.toLong())
            .fetch()
    }
}
