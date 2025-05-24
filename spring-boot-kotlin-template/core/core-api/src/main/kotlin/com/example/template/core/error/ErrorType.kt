package com.example.template.core.error

import org.springframework.boot.logging.LogLevel

enum class ErrorType(val kind: ErrorKind, val message: String, val logLevel: LogLevel) {
    DEFAULT_ERROR(ErrorKind.SERVER_ERROR, "서버에 문제가 발생했습니다.", LogLevel.ERROR),
}
