package com.example.template.test.api.docs

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@Tag("restdocs")
@ExtendWith(RestDocumentationExtension::class)
abstract class RestDocsTest {

    private lateinit var mockMvc: MockMvcRequestSpecification
    private lateinit var restDocumentation: RestDocumentationContextProvider
    private val objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder()
        .createXmlMapper(false)
        .build()

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        this.restDocumentation = restDocumentation
    }

    protected fun given(): MockMvcRequestSpecification {
        return mockMvc
    }

    protected fun mockController(controller: Any) {
        val mockMvc = createMockMvc(controller)
        this.mockMvc = RestAssuredMockMvc.given().mockMvc(mockMvc)
    }

    private fun createMockMvc(controller: Any): MockMvc {
        return MockMvcBuilders.standaloneSetup(controller)
            .apply<StandaloneMockMvcBuilder>(
                documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(
                        Preprocessors.modifyUris().scheme("https").host("example.com").removePort(),
                        Preprocessors.prettyPrint()
                    )
                    .withResponseDefaults(
                        Preprocessors.prettyPrint()
                    )
            )
            .setMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
            .build()
    }
}