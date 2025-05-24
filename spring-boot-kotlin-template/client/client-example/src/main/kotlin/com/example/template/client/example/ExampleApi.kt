package com.example.template.client.example

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(value = "example-api", url = "\${example.api.url}")
internal interface ExampleApi {

    @PostMapping(
        value = ["/example/example-api"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun example(@RequestBody request: ExampleRequestDto): ExampleResponseDto
}
