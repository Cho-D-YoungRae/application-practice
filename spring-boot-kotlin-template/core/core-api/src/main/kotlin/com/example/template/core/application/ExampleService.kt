package com.example.template.core.application

import com.example.template.core.domain.ExampleData
import com.example.template.core.domain.ExampleResult
import org.springframework.stereotype.Service

@Service
class ExampleService() {
    fun processExample(exampleData: ExampleData): ExampleResult {
        return ExampleResult(exampleData.value)
    }
}