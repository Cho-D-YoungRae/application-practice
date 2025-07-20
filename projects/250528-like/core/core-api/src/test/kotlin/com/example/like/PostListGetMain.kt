package com.example.like

import com.example.like.core.api.controller.response.ListResponse
import com.example.like.core.api.controller.response.PostResponse
import org.springframework.core.ParameterizedTypeReference

fun main(args: Array<String>) {
    val version = getVersionArg(args)
    val sort = getSortArg(args)
    val restClient = createRestClient()

    val startMs = System.currentTimeMillis()

    var page = 1
    var totalPosts = 0
    val pageSize = 20
    var hasNextPage = true
    val bodyType = object : ParameterizedTypeReference<ListResponse<PostResponse>>() {}

    while (hasNextPage) {
        val postResponses =
            restClient
                .get()
                .uri("$version/posts?page=$page&sort=$sort&size=$pageSize")
                .retrieve()
                .body(bodyType)!!
                .content

        if (postResponses.isEmpty()) {
            hasNextPage = false
        } else {
            totalPosts += postResponses.size
            page++
        }
    }

    val endMs = System.currentTimeMillis()
    val time = endMs - startMs
    println("Total posts fetched: $totalPosts pages: $page in time: $time ms. average: ${time / page} ms per page")
}
