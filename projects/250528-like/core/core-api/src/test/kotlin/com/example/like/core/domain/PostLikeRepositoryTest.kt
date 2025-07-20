package com.example.like.core.domain

import com.example.like.storage.db.core.PostJpaRepository
import com.example.like.storage.db.core.PostMetaJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PostLikeRepositoryTest(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postJpaRepository: PostJpaRepository,
    private val postMetaJpaRepository: PostMetaJpaRepository,
) {

    @AfterEach
    fun tearDown() {
        postJpaRepository.deleteAll()
        postMetaJpaRepository.deleteAll()
    }

    @Test
    fun `좋아요 동시성`() {
        // given
        val post = postRepository.addWithMeta(NewPost(title = "title", content = "content"))
        val threadCount = 100
        val es = Executors.newFixedThreadPool(threadCount)
        val barrier = CyclicBarrier(threadCount)
        val latch = CountDownLatch(threadCount)

        // when
        for (userId in 1..threadCount) {
            es.submit {
                try {
                    barrier.await()
                    postLikeRepository.likeWithMeta(PostLike(postId = post.id, userId = UserId(userId.toLong())))
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val likeCount = postLikeRepository.getCountsWithMeta(listOf(post.id)).first()
        assertThat(likeCount.postId).isEqualTo(post.id)
        assertThat(likeCount.count).isEqualTo(threadCount)
    }
}