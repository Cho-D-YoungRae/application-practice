package com.example.like.core.domain.config

import com.example.like.core.error.CoreException
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.boot.logging.LogLevel
import java.lang.reflect.Method

class AsyncExceptionHandler: AsyncUncaughtExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)
    private val logTemplate = "{} : {}"

    override fun handleUncaughtException(ex: Throwable, method: Method, vararg params: Any?) {
        if (ex is CoreException) {
            when (ex.errorType.logLevel) {
                LogLevel.ERROR -> log.error(exceptionLogMessage(ex), ex)
                LogLevel.WARN -> log.warn(exceptionLogMessage(ex), ex)
                else -> log.info(exceptionLogMessage(ex), ex)
            }
        } else {
            log.error(exceptionLogMessage(ex), ex)
        }
    }

    private fun exceptionLogMessage(exception: Throwable) =
        "[${exception.javaClass.simpleName}]: ${exception.message}"
}