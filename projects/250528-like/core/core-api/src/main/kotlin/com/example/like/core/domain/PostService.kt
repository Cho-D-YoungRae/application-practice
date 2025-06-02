package com.example.like.core.domain

interface PostService {

    fun create(newPost: NewPost)

    fun getList(query: PostListQuery): List<Post>

    fun getLikeCounts(posts: List<Post>): Map<PostId, Int>

    fun like(postLike: PostLike)
}