package com.example.like.core.domain

import com.example.like.core.enums.ListOrder
import com.example.like.core.enums.PostSortType


data class PostListQuery(
    val page: Int = 1,
    val size: Int = 20,
    val order: ListOrder = ListOrder.DESC,
    val sortType: PostSortType = PostSortType.CREATED_AT
)