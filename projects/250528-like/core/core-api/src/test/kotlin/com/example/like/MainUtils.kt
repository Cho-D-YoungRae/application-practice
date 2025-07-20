package com.example.like

import org.springframework.boot.DefaultApplicationArguments
import org.springframework.web.client.RestClient

fun getVersionArg(args: Array<String>): String {
    val argValues = DefaultApplicationArguments(*args).getOptionValues("v")
    if (argValues == null || argValues.isEmpty()) {
        throw IllegalArgumentException("Version argument is required")
    }
    return argValues.first()
}

fun getSortArg(args: Array<String>): String {
    val argValues = DefaultApplicationArguments(*args).getOptionValues("sort")
    if (argValues == null || argValues.isEmpty()) {
        throw IllegalArgumentException("Sort argument is required")
    }
    return argValues.first()
}

fun createRestClient(): RestClient {
    return RestClient.create("http://localhost:8080")
}