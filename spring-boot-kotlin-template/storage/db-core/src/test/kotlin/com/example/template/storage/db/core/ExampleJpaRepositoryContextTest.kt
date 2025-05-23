package com.example.template.storage.db.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExampleJpaRepositoryContextTest(
    private val sut: ExampleJpaRepository
): CoreDbContextTest() {

    @Test
    fun test() {
        val saved = sut.save(ExampleEntity("test"))

        val found = sut.findById(saved.id!!).get()
        assertThat(found.value).isEqualTo(saved.value)
    }
}