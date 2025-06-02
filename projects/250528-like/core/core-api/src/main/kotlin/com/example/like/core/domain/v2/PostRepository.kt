package com.example.like.core.domain.v2

import com.example.like.core.domain.NewPost
import com.example.like.core.domain.Post
import com.example.like.core.domain.PostId
import com.example.like.core.domain.PostListQuery
import com.example.like.storage.db.core.PostEntity
import com.example.like.storage.db.core.PostJpaRepository
import com.example.like.storage.db.core.PostMetaEntity
import com.example.like.storage.db.core.PostMetaJpaRepository
import com.example.template.core.enums.PostSortType
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository,
    private val postMetaJpaRepository: PostMetaJpaRepository
) {

    @Transactional
    fun add(newPost: NewPost): PostId {
        val postEntity = postJpaRepository.save(
            PostEntity(newPost.title, newPost.content)
        )
        postMetaJpaRepository.save(PostMetaEntity(postId = postEntity.id!!))
        return PostId(postEntity.id!!)
    }

    fun exists(postId: PostId): Boolean {
        return postJpaRepository.existsByIdAndDeletedIsFalse(postId.value)
    }

    fun find(query: PostListQuery): List<Post> {
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