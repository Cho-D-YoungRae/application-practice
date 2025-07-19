package com.example.like.worker

import com.example.like.core.event.PostLikeEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PostLikeEventConsumer(
    private val postMetaRepository: PostMetaRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${app.event.post-like.topic-name}"])
    fun postLikeEventListener(events: List<PostLikeEvent>) {
        log.debug("이벤트 처리. event={}", events)
        postMetaRepository.likeCountUp(events.map { it.postId })
    }
}
