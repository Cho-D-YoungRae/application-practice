package com.example.like.storage.db.core

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostLikeJpaRepository : JpaRepository<PostLikeEntity, Long> {
    
}
