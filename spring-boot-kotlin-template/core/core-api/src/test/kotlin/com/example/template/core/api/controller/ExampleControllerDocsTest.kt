package com.example.template.core.api.controller

import com.example.template.core.api.controller.request.ExampleRequest
import com.example.template.core.domain.ExampleResult
import com.example.template.core.domain.ExampleService
import com.example.template.test.api.docs.RestDocsTest
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*

class ExampleControllerDocsTest: RestDocsTest() {

    private lateinit var exampleService: ExampleService
    private lateinit var controller: ExampleController

    @BeforeEach
    fun setUp() {
        exampleService = mockk()
        controller = ExampleController(exampleService)
        mockController(controller)
    }

    @Test
    fun exampleGet() {
        every { exampleService.processExample(any()) } returns ExampleResult("Example")

        given()
            .contentType(ContentType.JSON)
            .queryParam("exampleParam", "HELLO_PARAM")
            .get("/get/{exampleValue}", "HELLO_PATH")

            .then()
            .status(HttpStatus.OK)
            .apply(
                document("example/example-get",
                    pathParameters(
                        parameterWithName("exampleValue").description("ExampleValue"),
                    ),
                    queryParameters(
                        parameterWithName("exampleParam").description("ExampleParam"),
                    ),
                    responseFields(
                        fieldWithPath("result").description("Result"),
                    ),
                ),
            )
    }

    @Test
    fun examplePost() {
        every { exampleService.processExample(any()) } returns ExampleResult("BYE")

        given()
            .contentType(ContentType.JSON)
            .body(ExampleRequest("HELLO_BODY"))
            .post("/post")

            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "example/example-post",
                    requestFields(
                        fieldWithPath("data").type(JsonFieldType.STRING).description("ExampleBody Data Field"),
                    ),
                ),
            )
    }
}