package com.example.like.core.domain.v1

import com.example.like.core.domain.NewPost
import org.springframework.stereotype.Service

@Service
class PostServiceV1(
    private val postRepository: PostRepository
) {

    fun create(newPost: NewPost) {

    }
}