package com.example.like.core.domain.v1

import com.example.like.core.domain.NewPost
import com.example.like.core.domain.PostLike
import com.example.like.core.domain.PostListQuery
import com.example.like.core.error.CoreException
import com.example.like.core.error.ErrorType
import org.springframework.stereotype.Service

@Service
class PostServiceV1(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository
) {

    fun create(newPost: NewPost) {
        postRepository.add(newPost)
    }

    fun getList(query: PostListQuery) {
        postRepository
    }

    fun like(postLike: PostLike) {
        if (!postRepository.exists(postLike.postId)) {
            throw CoreException(ErrorType.POST_NOT_FOUND, "postId=" + postLike.postId)
        }
        postLikeRepository.like(postLike)
    }
}