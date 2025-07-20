package com.example.like

import com.example.like.core.api.controller.request.PostCreateRequest

fun main(args: Array<String>) {

    val version = getVersionArg(args)
    val restClient = createRestClient()

    val startMs = System.currentTimeMillis()

    val postCount = 1000
    for (i in 1..postCount) {
        val postCreateRequest = PostCreateRequest(
            title = "title-$i",
            content = "content-$i",
        )
        restClient.post()
            .uri("/{version}/posts", version)
            .body(postCreateRequest)
            .retrieve()
            .body(Unit::class.java)
    }

    val endMs = System.currentTimeMillis()
    val time = endMs - startMs
    println("Created $postCount posts in $time ms. average: ${time / postCount} ms per post")
}
