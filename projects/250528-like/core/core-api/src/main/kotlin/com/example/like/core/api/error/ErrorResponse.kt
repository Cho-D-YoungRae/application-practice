package com.example.like.core.api.error

import com.example.like.core.error.ErrorType

data class ErrorResponse private constructor(
    val type: ErrorType,
    val message: String
) {

    constructor(type: ErrorType) : this(
        type = type,
        message = type.message
    )

}