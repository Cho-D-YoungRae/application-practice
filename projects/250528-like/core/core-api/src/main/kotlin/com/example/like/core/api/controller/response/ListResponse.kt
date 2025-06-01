package com.example.like.core.api.controller.response

data class ListResponse<T>(
    val content: List<T>
)

fun <T, R> Collection<T>.toResponse(mapper: (T) -> R): ListResponse<R> {
    return ListResponse(this.map(mapper).toList())
}
