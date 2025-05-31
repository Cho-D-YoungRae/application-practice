package com.example.like.storage.db.core.lock

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener
import org.springframework.retry.support.RetryTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import java.time.Duration

@Component
class OptimisticLockTemplate(
    private val txTemplate: TransactionTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val retryTemplate = RetryTemplateBuilder()
        .retryOn(OptimisticLockingFailureException::class.java)
        .maxAttempts(5)
        .uniformRandomBackoff(Duration.ofMillis(100), Duration.ofMillis(300))
        .withListener(OptimisticLockFailureRetryListener(log))
        .build()

    fun <T> execute(action: () -> T): T {
        return txTemplate.execute { action.invoke() }!!
    }

    fun execute(action: () -> Unit) {
        txTemplate.execute { action.invoke() }
    }
}

private class OptimisticLockFailureRetryListener(log: Logger) : RetryListener {

    override fun <T : Any, E : Throwable> onError(
        context: RetryContext,
        callback: RetryCallback<T, E>,
        throwable: Throwable
    ) {

    }
}