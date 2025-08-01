package com.example.like.core.domain

import com.example.like.core.enums.PostSortType
import com.example.like.storage.db.core.PostEntity
import com.example.like.storage.db.core.PostJpaRepository
import com.example.like.storage.db.core.PostLikeJpaRepository
import com.example.like.storage.db.core.PostMetaEntity
import com.example.like.storage.db.core.PostMetaJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository,
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val postMetaJpaRepository: PostMetaJpaRepository,
) {
    fun add(newPost: NewPost): Post {
        return mapEntityToPost(doAdd(newPost))
    }

    @Transactional
    fun addWithMeta(newPost: NewPost): Post {
        val postEntity = doAdd(newPost)
        postMetaJpaRepository.save(PostMetaEntity(postId = postEntity.id!!))
        return mapEntityToPost(postEntity)
    }

    private fun doAdd(newPost: NewPost): PostEntity =
        postJpaRepository.save(
            PostEntity(
                title = newPost.title,
                content = newPost.content,
            ),
        )

    fun exists(postId: PostId): Boolean {
        return postJpaRepository.existsById(postId.value)
    }

    fun find(query: PostListQuery): List<Post> {
        return when (query.sortType) {
            PostSortType.CREATED_AT ->
                postJpaRepository.findListByOrderByCreatedAt(
                    page = query.page - 1,
                    size = query.size,
                    order = query.order,
                )

            PostSortType.LIKE_COUNT ->
                postLikeJpaRepository.findCountsOrderByLikeCount(
                    page = query.page - 1,
                    size = query.size,
                    order = query.order,
                ).associate { it.postId to it.likeCount }
                    .let { postIdToCount ->
                        postJpaRepository.findAllById(postIdToCount.keys)
                            .sortedByDescending { postIdToCount[it.id!!] }
                    }
        }.map {
            mapEntityToPost(it)
        }
    }

    fun findWithMeta(query: PostListQuery): List<Post> {
        return when (query.sortType) {
            PostSortType.CREATED_AT ->
                postJpaRepository.findListByOrderByCreatedAt(
                    page = query.page - 1,
                    size = query.size,
                    order = query.order,
                )

            PostSortType.LIKE_COUNT ->
                postMetaJpaRepository.findPostCountsOrderByLikeCount(
                    page = query.page - 1,
                    size = query.size,
                    order = query.order,
                ).associate { it.postId to it.likeCount }
                    .let { postIdToCount ->
                        postJpaRepository.findAllById(postIdToCount.keys)
                            .sortedByDescending { postIdToCount[it.id!!] }
                    }
        }.map {
            mapEntityToPost(it)
        }
    }

    private fun mapEntityToPost(entity: PostEntity): Post =
        Post(
            id = PostId(entity.id!!),
            title = entity.title,
            content = entity.content,
            createdAt = entity.createdAt!!,
        )
}
