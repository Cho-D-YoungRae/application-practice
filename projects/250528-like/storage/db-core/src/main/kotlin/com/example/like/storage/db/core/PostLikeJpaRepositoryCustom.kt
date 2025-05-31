package com.example.like.storage.db.core

import com.example.template.core.enums.ListOrder

interface PostLikeJpaRepositoryCustom {

    fun countByPostIdInAndGroupByPostId(postIds: Collection<Long>): List<PostLikeCountProjection>

    fun findPostIdsOrderByLikeCount(postId: Long, size: Int, order: ListOrder): List<Long>

}