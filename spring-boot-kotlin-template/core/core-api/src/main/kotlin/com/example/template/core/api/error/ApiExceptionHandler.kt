package com.example.template.core.api.error

import com.example.template.core.error.CoreException
import com.example.template.core.error.ErrorKind
import com.example.template.core.error.ErrorType
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ApiExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(CoreException::class)
    private fun handleCoreException(ex: CoreException): ResponseEntity<ErrorResponse?> {
        when (ex.errorType.logLevel) {
            LogLevel.ERROR -> logger.error(exceptionLogMessage(ex), ex)
            LogLevel.WARN -> logger.warn(exceptionLogMessage(ex), ex)
            else -> logger.info(exceptionLogMessage(ex), ex)
        }
        val status = getHttpStatusCode(ex.errorType.kind)
        val response = ErrorResponse(ex.errorType)
        return ResponseEntity
            .status(status)
            .body<ErrorResponse>(response)
    }

    private fun getHttpStatusCode(kind: ErrorKind): HttpStatus {
        return when (kind) {
            ErrorKind.SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
            ErrorKind.UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE
            ErrorKind.VALIDATION_FAILED, ErrorKind.ILLEGAL_STATE -> HttpStatus.BAD_REQUEST
            ErrorKind.NOT_FOUND -> HttpStatus.NOT_FOUND
            ErrorKind.DUPLICATED -> HttpStatus.CONFLICT
            ErrorKind.FORBIDDEN -> HttpStatus.FORBIDDEN
        }
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(ex: Exception): ResponseEntity<ErrorResponse?> {
        logger.error(exceptionLogMessage(ex), ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body<ErrorResponse>(ErrorResponse(ErrorType.DEFAULT_ERROR))
    }

    private fun exceptionLogMessage(exception: Exception) =
        "[${exception.javaClass.simpleName}]: ${exception.message}"
}