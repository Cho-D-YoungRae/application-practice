package com.example.like.core.api.controller

import com.example.like.core.api.controller.request.PostCreateRequest
import com.example.like.core.api.controller.request.PostListQueryRequest
import com.example.like.core.api.controller.response.ListResponse
import com.example.like.core.api.controller.response.PostResponse
import com.example.like.core.api.controller.response.toResponse
import com.example.like.core.domain.*
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class PostController(
    private val postServices: List<PostService>
) {

    @PostMapping("/{version}/posts")
    fun create(
        @PathVariable version: String,
        @Valid @RequestBody request: PostCreateRequest
    ) {
        service(version).create(request.toNewPost())
    }

    @GetMapping("/{version}/posts")
    fun getList(
        @PathVariable version: String,
        @Valid @ModelAttribute request: PostListQueryRequest
    ): ListResponse<PostResponse> {
        val posts = service(version).getList(request.toPostListQuery())
        val likeCounts = service(version).getLikeCounts(posts)
        return posts.toResponse {
            PostResponse(
                id = it.id.value,
                title = it.title,
                content = it.content,
                likeCount = likeCounts.getOrDefault(it.id, 0),
                createdAt = it.createdAt
            )
        }
    }

    @PutMapping("/{version}/posts/{postId}/like")
    fun like(
        @PathVariable version: String,
        @RequestHeader("User-Id") userId: Long,
        @PathVariable("postId") postId: Long
    ) {
        service(version).like(PostLike(UserId(userId), PostId(postId)))
    }

    private fun service(version: String): PostService = when(version) {
        "v1" -> postServices.find { it is PostServiceV1 }!!
        "v2" -> postServices.find { it is PostServiceV2 }!!
        "v3" -> postServices.find { it is PostServiceV3 }!!
        else -> throw IllegalArgumentException("지원하지 않는 버전입니다: $version")
    }
}
