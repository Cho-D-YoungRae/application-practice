package com.example.like.core.event.local

import com.example.like.core.event.PostLikeEventProperties
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    name = ["app.event.post-like.init.enabled"],
    havingValue = "true",
    matchIfMissing = false,
)
class PostLikeInitializer(
    private val kafkaAdmin: KafkaAdmin,
    private val properties: PostLikeEventProperties,
) : ApplicationRunner {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val topicName = properties.topicName
        log.info("Kafka 토픽 초기화: {}", topicName)

        val newTopic = NewTopic(topicName, 3, 2.toShort())

        kafkaAdmin.createOrModifyTopics(newTopic)
    }
}
