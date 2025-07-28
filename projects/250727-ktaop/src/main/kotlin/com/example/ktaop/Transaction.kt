package com.example.ktaop

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private val log = LoggerFactory.getLogger(Transaction::class.java)

@Component
class Transaction(
    _txAdvice: Advice
) {
    init {
        txAdvice = _txAdvice
    }

    companion object {
        private var txAdvice: Advice? = null

        fun <T> run(function: () -> T): T {
            return txAdvice?.run(function) ?: run {
                logTransactionNotConfigured()
                function.invoke()
            }
        }

        fun <T> runReadOnly(function: () -> T): T {
            return txAdvice?.runReadOnly(function) ?: run {
                logTransactionNotConfigured()
                function.invoke()
            }
        }

        private fun logTransactionNotConfigured() {
            log.warn("트랜잭션이 설정되지 않았습니다.")
        }
    }

    @Component
    class Advice {
        @Transactional
        fun <T> run(function: () -> T): T {
            return function.invoke()
        }

        @Transactional(readOnly = true)
        fun <T> runReadOnly(function: () -> T): T {
            return function.invoke()
        }
    }
}
