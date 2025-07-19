package com.example.like.storage.db.core

import com.example.like.core.enums.ListOrder
import com.example.like.storage.db.core.QPostEntity.postEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager

class PostJpaRepositoryCustomImpl(
    entityManager: EntityManager,
) : PostJpaRepositoryCustom {
    private val queryFactory = JPAQueryFactory(entityManager)

    override fun findListByOrderByCreatedAt(
        page: Int,
        size: Int,
        order: ListOrder,
    ): List<PostEntity> {
        return queryFactory
            .selectFrom(postEntity)
            .orderBy(postEntity.createdAt.toOrder(order))
            .offset((page * size).toLong())
            .limit(size.toLong())
            .fetch()
    }
}
