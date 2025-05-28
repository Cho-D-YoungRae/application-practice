package com.example.like.core.api.controller

import com.example.like.core.api.controller.request.ExampleRequest
import com.example.like.core.api.controller.response.ExampleResponse
import com.example.like.core.domain.ExampleData
import com.example.like.core.domain.ExampleService
import org.springframework.web.bind.annotation.*

@RestController
class ExampleController(
    val exampleService: ExampleService,
) {
    @GetMapping("/get/{exampleValue}")
    fun exampleGet(
        @PathVariable exampleValue: String,
        @RequestParam exampleParam: String,
    ): ExampleResponse {
        val result = exampleService.processExample(ExampleData(exampleValue, exampleParam))
        return ExampleResponse(result.data)
    }

    @PostMapping("/post")
    fun examplePost(
        @RequestBody request: ExampleRequest,
    ) = Unit
}
