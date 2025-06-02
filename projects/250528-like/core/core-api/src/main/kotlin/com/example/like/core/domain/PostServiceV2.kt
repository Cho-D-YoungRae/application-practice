package com.example.like.core.domain

import com.example.like.core.error.CoreException
import com.example.like.core.error.ErrorType
import org.springframework.stereotype.Service

@Service
class PostServiceV2(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository
) {

    fun create(newPost: NewPost) {
        postRepository.addWithMeta(newPost)
    }

    fun getList(query: PostListQuery): List<Post> {
        return postRepository.findWithMeta(query)
    }

    fun getLikeCounts(posts: List<Post>): Map<PostId, Int> {
        return postLikeRepository.getCountsWithMeta(posts.map { it.id })
            .associate { it.postId to it.count }
    }

    fun like(postLike: PostLike) {
        if (!postRepository.exists(postLike.postId)) {
            throw CoreException(ErrorType.POST_NOT_FOUND, "postId=" + postLike.postId)
        }
        postLikeRepository.likeWithMeta(postLike)
    }
}