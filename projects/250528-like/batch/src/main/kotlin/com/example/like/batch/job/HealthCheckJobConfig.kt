package com.example.like.batch.job

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.Clock
import java.time.LocalDate
import java.time.OffsetDateTime

@Configuration
class HealthCheckJobConfig(
    private val clock: Clock,
    private val jobRepository: JobRepository,
    private val txManager: PlatformTransactionManager,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val JOB_NAME = "healthCheckJob"
        const val STEP_NAME = "healthCheckStep"
    }

    @Bean(JOB_NAME)
    fun job(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .incrementer(RunIdIncrementer())
            .start(step())
            .build()
    }

    @Bean(STEP_NAME)
    fun step(): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .tasklet({ _, _ ->
                log.info("<<<<<<<<<<<< 헬스체크 >>>>>>>>>>>>")
                log.info("timezone: {}", clock.zone)
                log.info("date: {}", LocalDate.now(clock))
                log.info("time: {}", OffsetDateTime.now(clock))
                RepeatStatus.FINISHED
            }, txManager)
            .build()
    }
}
