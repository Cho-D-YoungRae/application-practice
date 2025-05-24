package com.example.template.core.domain.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig(private val taskExecutor: TaskExecutor): AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        return taskExecutor
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return AsyncExceptionHandler()
    }
}