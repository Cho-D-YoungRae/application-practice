package com.example.like.batch.job

import com.example.like.storage.db.core.PostEntity
import com.example.like.storage.db.core.PostLikeJpaRepository
import com.example.like.storage.db.core.PostMetaEntity
import com.example.like.storage.db.core.PostMetaJpaRepository
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

private const val JOB_NAME = "postLikeCountJob"
private const val STEP_NAME = "postLikeCountStep"
private const val CHUNK_SIZE = 100

@Configuration
class PostLikeCountJobConfig(
    private val jobRepository: JobRepository,
    private val txManager: PlatformTransactionManager,
) {
    @Bean(JOB_NAME)
    fun job(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(step())
            .incrementer(RunIdIncrementer())
            .build()
    }

    @Bean(STEP_NAME)
    @JobScope
    fun step(): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .chunk<PostEntity, PostEntity>(CHUNK_SIZE, txManager)
            .reader(reader())
            .writer(writer())
            .build()
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    fun reader(entityManagerFactory: EntityManagerFactory? = null): JpaPagingItemReader<PostEntity> {
        return JpaPagingItemReaderBuilder<PostEntity>()
            .name(STEP_NAME + "ItemReader")
            .entityManagerFactory(entityManagerFactory!!)
            .pageSize(CHUNK_SIZE)
            .transacted(false)
            .queryString("SELECT p FROM PostEntity p")
            .build()
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    fun writer(
        postLikeJpaRepository: PostLikeJpaRepository? = null,
        postMetaJpaRepository: PostMetaJpaRepository? = null,
        entityManagerFactory: EntityManagerFactory? = null,
    ): PostLikeCountItemWriter {
        val jpaItemWriter =
            JpaItemWriterBuilder<PostMetaEntity>()
                .entityManagerFactory(entityManagerFactory!!)
                .usePersist(false)
                .build()

        return PostLikeCountItemWriter(
            postLikeJpaRepository!!,
            postMetaJpaRepository!!,
            jpaItemWriter,
        )
    }

    open class PostLikeCountItemWriter(
        private val postLikeJpaRepository: PostLikeJpaRepository,
        private val postMetaJpaRepository: PostMetaJpaRepository,
        private val jpaItemWriter: JpaItemWriter<PostMetaEntity>,
    ) : ItemWriter<PostEntity>, InitializingBean by jpaItemWriter {
        override fun write(chunk: Chunk<out PostEntity>) {
            val postIds = chunk.items.map { it.id!! }

            val postIdToLikeCount =
                postLikeJpaRepository
                    .countByPostIdInAndGroupByPostId(postIds)
                    .associateBy {
                        it.postId
                    }

            val postMetaEntities = postMetaJpaRepository.findAllWithPessimisticLockByPostIdIn(postIds)

            postMetaEntities.forEach {
                it.update(postIdToLikeCount[it.postId]?.likeCount?.toInt() ?: 0)
            }

            jpaItemWriter.write(Chunk(postMetaEntities))
        }
    }
}
