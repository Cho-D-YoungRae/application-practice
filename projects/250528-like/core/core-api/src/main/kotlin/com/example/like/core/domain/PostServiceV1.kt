package com.example.like.core.domain

import com.example.like.core.error.CoreException
import com.example.like.core.error.ErrorType
import org.springframework.stereotype.Service

@Service
class PostServiceV1(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
) : PostService {
    override fun create(newPost: NewPost): Post {
        return postRepository.add(newPost)
    }

    override fun getList(query: PostListQuery): List<Post> {
        return postRepository.find(query)
    }

    override fun getLikeCounts(posts: List<Post>): Map<PostId, Int> {
        return postLikeRepository.getCounts(posts.map { it.id })
            .associate { it.postId to it.count }
    }

    override fun like(postLike: PostLike) {
        if (!postRepository.exists(postLike.postId)) {
            throw CoreException(ErrorType.POST_NOT_FOUND, "postId=" + postLike.postId)
        }
        postLikeRepository.like(postLike)
    }
}
