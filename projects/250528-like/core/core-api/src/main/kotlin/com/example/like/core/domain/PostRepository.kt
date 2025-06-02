package com.example.like.core.domain

import com.example.like.storage.db.core.*
import com.example.like.core.enums.PostSortType
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository,
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val postMetaJpaRepository: PostMetaJpaRepository
) {

    fun addWithoutMeta(newPost: NewPost): PostId {
        return PostId(
            postJpaRepository.save(
                PostEntity(newPost.title, newPost.content)
            ).id!!
        )
    }

    @Transactional
    fun addWithMeta(newPost: NewPost): PostId {
        val postEntity = postJpaRepository.save(
            PostEntity(newPost.title, newPost.content)
        )
        postMetaJpaRepository.save(PostMetaEntity(postId = postEntity.id!!))
        return PostId(postEntity.id!!)
    }

    fun exists(postId: PostId): Boolean {
        return postJpaRepository.existsByIdAndDeletedIsFalse(postId.value)
    }

    fun findWithoutMeta(query: PostListQuery): List<Post> {
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

    fun findWithMeta(query: PostListQuery): List<Post> {
        return when (query.sortType) {
            PostSortType.CREATED_AT -> postJpaRepository.findListByOrderByCreatedAt(
                page = query.page - 1, size = query.size, order = query.order
            )

            PostSortType.LIKE_COUNT -> postMetaJpaRepository.findPostIdsOrderByLikeCount(
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