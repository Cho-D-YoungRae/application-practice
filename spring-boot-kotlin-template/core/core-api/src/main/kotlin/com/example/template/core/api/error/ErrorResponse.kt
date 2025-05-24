package com.example.template.core.api.error

import com.example.template.core.error.ErrorType

data class ErrorResponse private constructor(
    val type: ErrorType,
    val message: String
) {

    constructor(type: ErrorType) : this(
        type = type,
        message = type.message
    )

}