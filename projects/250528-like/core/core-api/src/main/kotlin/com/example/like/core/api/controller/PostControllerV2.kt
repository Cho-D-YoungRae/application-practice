package com.example.like.core.api.controller

import com.example.like.core.api.controller.request.PostCreateRequest
import com.example.like.core.api.controller.request.PostListQueryRequest
import com.example.like.core.api.controller.response.ListResponse
import com.example.like.core.api.controller.response.PostResponse
import com.example.like.core.api.controller.response.toResponse
import com.example.like.core.domain.PostId
import com.example.like.core.domain.PostLike
import com.example.like.core.domain.PostServiceV2
import com.example.like.core.domain.UserId
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2")
class PostControllerV2(
    private val postService: PostServiceV2
) {

    @PostMapping("/posts")
    fun create(@Valid @RequestBody request: PostCreateRequest) {
        postService.create(request.toNewPost())
    }

    @GetMapping("/posts")
    fun getList(@Valid @ModelAttribute request: PostListQueryRequest): ListResponse<PostResponse> {
        val posts = postService.getList(request.toPostListQuery())
        val likeCounts = postService.getLikeCounts(posts)
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

    @PutMapping("/posts/{postId}/like")
    fun like(@RequestHeader("User-Id") userId: Long, @PathVariable("postId") postId: Long) {
        postService.like(PostLike(UserId(userId), PostId(postId)))
    }
}