package com.example.like.core.domain.v1

import com.example.like.core.domain.NewPost
import com.example.like.core.domain.PostLike
import org.springframework.stereotype.Service

@Service
class PostServiceV1(
    private val postRepository: PostRepository
) {

    fun create(newPost: NewPost) {
        postRepository.add(newPost)
    }

    fun getList() {

    }

    fun like(postLike: PostLike) {

    }
}