package com.example.like.core.api.controller.request

import com.example.like.core.domain.PostListQuery
import com.example.like.core.enums.ListOrder
import com.example.like.core.enums.PostSortType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive

data class PostListQueryRequest(
    @Positive val page: Int?,
    @Positive @Max(100) val size: Int?,
    val order: ListOrder?,
    val sortType: PostSortType?
) {

    fun toPostListQuery() = PostListQuery(
        page = this.page ?: PostListQuery().page,
        size = this.size ?: PostListQuery().size,
        order = this.order ?: PostListQuery().order,
        sortType = this.sortType ?: PostListQuery().sortType
    )
}
