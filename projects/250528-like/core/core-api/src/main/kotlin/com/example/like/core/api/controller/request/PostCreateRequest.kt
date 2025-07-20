package com.example.like.core.api.controller.request

import com.example.like.core.domain.NewPost
import jakarta.validation.constraints.NotBlank

data class PostCreateRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val content: String,
) {
    fun toNewPost() =
        NewPost(
            title = title,
            content = content,
        )
}
