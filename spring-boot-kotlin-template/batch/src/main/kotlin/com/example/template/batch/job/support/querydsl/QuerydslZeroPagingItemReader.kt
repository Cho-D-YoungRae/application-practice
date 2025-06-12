package com.example.template.batch.job.support.querydsl

import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManagerFactory
import org.springframework.util.ClassUtils
import java.util.function.Function

/**
 * https://github.com/jojoldu/spring-batch-querydsl
 */
class QuerydslZeroPagingItemReader<T>(
    entityManagerFactory: EntityManagerFactory,
    pageSize: Int,
    queryFunction: Function<JPAQueryFactory, JPAQuery<T>>,
    transacted: Boolean = true,
    name: String = ClassUtils.getShortName(QuerydslZeroPagingItemReader::class.java)
): QuerydslPagingItemReader<T>(
    entityManagerFactory = entityManagerFactory,
    pageSize = pageSize,
    queryFunction = queryFunction,
    transacted = transacted,
    saveSate = false,
    name = name
) {

    override fun doReadPage() {
        val tx = getTxOrNull()

        val query: JPQLQuery<T> = createQuery()
            .offset(0)
            .limit(pageSize.toLong())

        initResults()

        fetchQuery(query, tx)
    }
}