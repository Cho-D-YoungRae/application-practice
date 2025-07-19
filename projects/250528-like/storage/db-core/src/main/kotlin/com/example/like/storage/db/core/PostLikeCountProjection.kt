package com.example.like.storage.db.core

import com.querydsl.core.annotations.QueryProjection

data class PostLikeCountProjection
    @QueryProjection
    constructor(
        val postId: Long,
        val likeCount: Long,
    )
