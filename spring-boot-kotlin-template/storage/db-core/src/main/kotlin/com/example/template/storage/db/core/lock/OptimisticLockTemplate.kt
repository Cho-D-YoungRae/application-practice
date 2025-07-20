package com.example.template.storage.db.core.lock

import org.slf4j.LoggerFactory
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener
import org.springframework.retry.support.RetryTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import java.time.Duration

private const val MAX_ATTEMPTS = 5

@Component
class OptimisticLockTemplate(
    private val txTemplate: TransactionTemplate,
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val retryTemplate =
        RetryTemplateBuilder()
            .retryOn(OptimisticLockingFailureException::class.java)
            .maxAttempts(MAX_ATTEMPTS)
            .uniformRandomBackoff(Duration.ofMillis(100), Duration.ofMillis(300))
            .withListener(
                object : RetryListener {
                    override fun <T : Any, E : Throwable> onError(
                        context: RetryContext,
                        callback: RetryCallback<T, E>,
                        throwable: Throwable,
                    ) {
                        log.info(
                            "Optimistic lock failure detected. Retry count: [${context.retryCount}/$MAX_ATTEMPTS]",
                            throwable,
                        )
                    }
                },
            )
            .build()

    fun <T> execute(action: () -> T): T {
        return retryTemplate.execute<T, OptimisticLockingFailureException> {
            txTemplate.execute { action.invoke() }!!
        }
    }
}
