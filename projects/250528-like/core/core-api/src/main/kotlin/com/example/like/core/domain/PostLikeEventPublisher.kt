package com.example.like.core.domain

import com.example.like.core.event.PostLikeEvent
import com.example.like.core.event.PostLikeEventProperties
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PostLikeEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, PostLikeEvent>,
    private val properties: PostLikeEventProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun publish(postLike: PostLike) {
        kafkaTemplate.send(
            properties.topicName,
            "postId:${postLike.postId.value}",
            PostLikeEvent(
                postId = postLike.postId.value,
            ),
        ).thenAccept { log.debug("이벤트 발송 완료. record:{}", it.producerRecord) }
    }
}
