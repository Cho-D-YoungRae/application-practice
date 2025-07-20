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

    @KafkaListener(
        topics = ["\${app.event.post-like.topic-name}"],
        batch = "true",
        concurrency = "10"
    )
    fun postLikeEventListener(events: List<PostLikeEvent>) {
        val postIds = events.map { it.postId }
        log.debug("좋아요 이벤트 처리. postIds={}", postIds)
        postMetaRepository.likeCountUp(postIds)
    }
}
