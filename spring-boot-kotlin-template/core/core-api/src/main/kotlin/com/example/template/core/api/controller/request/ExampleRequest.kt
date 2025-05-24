package com.example.template.core.api.controller.request

import com.example.template.core.domain.ExampleData

data class ExampleRequest(
    val data: String,
) {
    fun toExampleData(): ExampleData {
        return ExampleData(data, data)
    }
}
