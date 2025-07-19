package com.example.like.storage.db.core

import com.example.like.core.enums.ListOrder

interface PostMetaJpaRepositoryCustom {
    fun findPostIdsOrderByLikeCount(
        page: Int,
        size: Int,
        order: ListOrder,
    ): List<Long>
}
