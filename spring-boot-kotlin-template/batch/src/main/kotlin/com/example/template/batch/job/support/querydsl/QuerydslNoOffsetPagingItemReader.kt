package com.example.template.batch.job.support.querydsl

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManagerFactory
import org.springframework.util.ClassUtils
import java.util.function.Function

class QuerydslNoOffsetPagingItemReader<T, ID>(
    entityManagerFactory: EntityManagerFactory,
    pageSize: Int,
    queryFunction: Function<JPAQueryFactory, JPAQuery<T>>,
    transacted: Boolean = true,
    name: String = ClassUtils.getShortName(QuerydslNoOffsetPagingItemReader::class.java)
): QuerydslPagingItemReader<T>(
    entityManagerFactory = entityManagerFactory,
    pageSize = pageSize,
    queryFunction = queryFunction,
    transacted = transacted,
    saveSate = false,
    name = name
) {

    private var lastId: ID? = null

}