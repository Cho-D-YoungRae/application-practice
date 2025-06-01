package com.example.like.storage.db.core

import com.example.template.core.enums.ListOrder

interface PostJpaRepositoryCustom {

    fun findListByOrderByCreatedAt(page: Int, size: Int, order: ListOrder): List<PostEntity>
}