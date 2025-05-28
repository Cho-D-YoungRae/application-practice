package com.example.like.core.domain.v1

import com.example.like.core.domain.NewPost
import com.example.like.core.domain.PostId
import com.example.like.storage.db.core.PostEntity
import com.example.like.storage.db.core.PostJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository
) {

    fun add(newPost: NewPost): PostId {
        return PostId(
            postJpaRepository.save(
                PostEntity(newPost.title, newPost.content)
            ).id!!
        )

        fun exists(postId: PostId): Boolean {
            return postJpaRepository.existsByIdAndDeletedIsFalse(postId.value)
        }
    }
}