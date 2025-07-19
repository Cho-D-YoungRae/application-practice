package com.example.like.core.api.controller

import com.example.like.core.api.controller.request.PostCreateRequest
import com.example.like.core.api.controller.request.PostListQueryRequest
import com.example.like.core.api.controller.response.ListResponse
import com.example.like.core.api.controller.response.PostResponse
import com.example.like.core.api.controller.response.toResponse
import com.example.like.core.domain.PostId
import com.example.like.core.domain.PostLike
import com.example.like.core.domain.PostService
import com.example.like.core.domain.PostServiceV1
import com.example.like.core.domain.PostServiceV2
import com.example.like.core.domain.PostServiceV3
import com.example.like.core.domain.PostServiceV4
import com.example.like.core.domain.UserId
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    postServiceV1: PostServiceV1,
    postServiceV2: PostServiceV2,
    postServiceV3: PostServiceV3,
    postServiceV4: PostServiceV4,
) {
    private val versionToService =
        mapOf(
            "v1" to postServiceV1,
            "v2" to postServiceV2,
            "v3" to postServiceV3,
            "v4" to postServiceV4,
        )

    @PostMapping("/{version}/posts")
    fun create(
        @PathVariable version: String,
        @Valid @RequestBody request: PostCreateRequest,
    ) {
        service(version).create(request.toNewPost())
    }

    @GetMapping("/{version}/posts")
    fun getList(
        @PathVariable version: String,
        @Valid @ModelAttribute request: PostListQueryRequest,
    ): ListResponse<PostResponse> {
        val posts = service(version).getList(request.toPostListQuery())
        val likeCounts = service(version).getLikeCounts(posts)
        return posts.toResponse {
            PostResponse(
                id = it.id.value,
                title = it.title,
                content = it.content,
                likeCount = likeCounts.getOrDefault(it.id, 0),
                createdAt = it.createdAt,
            )
        }
    }

    @PutMapping("/{version}/posts/{postId}/like")
    fun like(
        @PathVariable version: String,
        @RequestHeader("User-Id") userId: Long,
        @PathVariable("postId") postId: Long,
    ) {
        service(version).like(PostLike(UserId(userId), PostId(postId)))
    }

    private fun service(version: String): PostService =
        versionToService[version]
            ?: throw IllegalArgumentException("지원하지 않는 버전입니다: $version")
}
