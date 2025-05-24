package com.example.template.core.api.test

import org.junit.jupiter.api.Tag
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@Tag("context")
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class ContextTest
