package com.example.like

import com.example.like.core.api.controller.response.ListResponse
import com.example.like.core.api.controller.response.PostResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.client.RestClient

fun main(args: Array<String>) {

    val version = getVersionArg(args)
    val restClient = createRestClient()
    val postIds = getPostIds(restClient, version).shuffled()

    val startMs = System.currentTimeMillis()
    var totalLikeCount = 0

    (1..postIds.size).toList().forEach {
        val postIdsToLike = postIds.subList(0, it)
        val userId = it
        println("Like ${postIdsToLike.size} posts for user $userId")
        postIdsToLike.forEach { postId ->
            restClient.put()
                .uri("${version}/posts/${postId}/like")
                .header("User-Id", userId.toString())
                .retrieve()
                .body(Unit::class.java)
            totalLikeCount++
        }
    }

    val endMs = System.currentTimeMillis()
    val time = endMs - startMs
    println("Total like count: $totalLikeCount in time $time ms. average: ${time / totalLikeCount} ms per like")
}

private fun getPostIds(
    restClient: RestClient,
    version: String
): List<Long> {
    val postIds = mutableListOf<Long>()

    val bodyType = object : ParameterizedTypeReference<ListResponse<PostResponse>>() {}
    var page = 1
    var hasNextPage = true
    while (hasNextPage) {
        val postResponses = restClient.get()
            .uri("${version}/posts?page=$page")
            .retrieve()
            .body(bodyType)!!
            .content
        if (postResponses.isEmpty()) {
            hasNextPage = false
        } else {
            postResponses.forEach { postResponse ->
                postIds.add(postResponse.id)
            }
            page++
        }
    }

    println("Total posts: ${postIds.size}")
    return postIds.toList()
}