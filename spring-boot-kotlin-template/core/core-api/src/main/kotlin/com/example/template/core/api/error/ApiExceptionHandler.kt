package com.example.template.core.api.error

import com.example.template.core.error.CoreException
import com.example.template.core.error.ErrorKind
import com.example.template.core.error.ErrorType
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Clock
import java.time.LocalDateTime

@RestControllerAdvice
class ApiExceptionHandler(private val clock: Clock): ResponseEntityExceptionHandler() {

    @ExceptionHandler(CoreException::class)
    fun handleCoreException(ex: CoreException): ProblemDetail {
        when (ex.errorType.logLevel) {
            LogLevel.ERROR -> logger.error(exceptionLogMessage(ex), ex)
            LogLevel.WARN -> logger.warn(exceptionLogMessage(ex), ex)
            else -> logger.info(exceptionLogMessage(ex), ex)
        }
        return createProblemDetail(ex.errorType)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ProblemDetail {
        logger.error(exceptionLogMessage(ex), ex)
        return createProblemDetail(ErrorType.DEFAULT_ERROR)
    }

    private fun exceptionLogMessage(exception: Exception) =
        "[${exception.javaClass.simpleName}]: ${exception.message}"

    private fun createProblemDetail(errorType: ErrorType): ProblemDetail {
        val status = getHttpStatusCode(errorType.kind)
        val problemDetail = ProblemDetail.forStatusAndDetail(status, errorType.message)
        problemDetail.setProperty("timestamp", LocalDateTime.now(clock))
        problemDetail.setProperty("errorType", errorType)
        return problemDetail
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
}