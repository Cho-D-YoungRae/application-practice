package com.example.like.core.error

import org.springframework.boot.logging.LogLevel

enum class ErrorType(val kind: ErrorKind, val message: String, val logLevel: LogLevel) {
    DEFAULT_ERROR(ErrorKind.SERVER_ERROR, "서버에 문제가 발생했습니다.", LogLevel.ERROR),

    POST_NOT_FOUND(ErrorKind.NOT_FOUND, "게시물이 존재하지 않습니다.", LogLevel.INFO),
}
