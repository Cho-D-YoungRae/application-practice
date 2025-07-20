package com.example.like.storage.db.core

import com.example.like.core.enums.ListOrder
import com.example.like.storage.db.core.QPostMetaEntity.postMetaEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager

class PostMetaJpaRepositoryCustomImpl(
    entityManager: EntityManager,
) : PostMetaJpaRepositoryCustom {
    private val queryFactory = JPAQueryFactory(entityManager)

    override fun findPostCountsOrderByLikeCount(
        page: Int,
        size: Int,
        order: ListOrder,
    ): List<PostLikeCountProjection> {
        return queryFactory
            .select(
                QPostLikeCountProjection(
                    postMetaEntity.postId,
                    postMetaEntity.likeCount,
                ),
            )
            .from(postMetaEntity)
            .orderBy(postMetaEntity.likeCount.desc())
            .offset((page * size).toLong())
            .limit(size.toLong())
            .fetch()
    }
}
