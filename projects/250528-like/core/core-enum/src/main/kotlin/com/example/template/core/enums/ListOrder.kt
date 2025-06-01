package com.example.template.core.enums

enum class ListOrder {

    DESC,
    ASC,
    ;

    fun opposite() = when(this) {
        DESC -> ASC
        ASC -> DESC
    }
}