package com.example.like.storage.db.core

import com.example.like.core.enums.ListOrder

interface PostMetaJpaRepositoryCustom {
    fun findPostCountsOrderByLikeCount(
        page: Int,
        size: Int,
        order: ListOrder,
    ): List<PostLikeCountProjection>
}
