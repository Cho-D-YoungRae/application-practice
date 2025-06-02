package com.example.like.storage.db.core

import com.example.like.core.enums.ListOrder

interface PostLikeJpaRepositoryCustom {

    fun countByPostIdInAndGroupByPostId(postIds: Collection<Long>): List<PostLikeCountProjection>

    fun findPostIdsOrderByLikeCount(page: Int, size: Int, order: ListOrder): List<Long>

}