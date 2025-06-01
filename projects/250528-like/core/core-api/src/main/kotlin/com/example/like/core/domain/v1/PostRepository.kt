package com.example.like.core.domain.v1

import com.example.like.core.domain.NewPost
import com.example.like.core.domain.Post
import com.example.like.core.domain.PostId
import com.example.like.core.domain.PostListQuery
import com.example.like.storage.db.core.PostEntity
import com.example.like.storage.db.core.PostJpaRepository
import com.example.like.storage.db.core.PostLikeJpaRepository
import com.example.template.core.enums.PostSortType
import org.springframework.stereotype.Repository

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository,
    private val postLikeJpaRepository: PostLikeJpaRepository
) {

    fun add(newPost: NewPost): PostId {
        return PostId(
            postJpaRepository.save(
                PostEntity(newPost.title, newPost.content)
            ).id!!
        )
    }

    fun exists(postId: PostId): Boolean {
        return postJpaRepository.existsByIdAndDeletedIsFalse(postId.value)
    }

    fun find(query: PostListQuery): List<Post> {
        return when (query.sortType) {
            PostSortType.CREATED_AT -> postJpaRepository.findListByOrderByCreatedAt(
                page = query.page - 1, size = query.size, order = query.order
            )

            PostSortType.LIKE_COUNT -> postLikeJpaRepository.findPostIdsOrderByLikeCount(
                page = query.page - 1, size = query.size, order = query.order
            ).let { postJpaRepository.findAllById(it) }
        }.map {
            Post(
                id = PostId(it.id!!),
                title = it.title,
                content = it.content,
                createdAt = it.createdAt!!
            )
        }
    }
}