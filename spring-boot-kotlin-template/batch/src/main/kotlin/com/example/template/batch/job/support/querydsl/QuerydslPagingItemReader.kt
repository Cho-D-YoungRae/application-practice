package com.example.template.batch.job.support.querydsl

import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import org.springframework.batch.item.database.AbstractPagingItemReader
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.util.ClassUtils
import org.springframework.util.CollectionUtils
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Function

/**
 * https://github.com/jojoldu/spring-batch-querydsl
 */
open class QuerydslPagingItemReader<T>(
    private val entityManagerFactory: EntityManagerFactory,
    pageSize: Int,
    private val queryFunction: Function<JPAQueryFactory, JPAQuery<T>>,
    private val transacted: Boolean = true,
    saveSate: Boolean = true,
    name: String = ClassUtils.getShortName(QuerydslPagingItemReader::class.java)
): AbstractPagingItemReader<T>() {

    protected val jpaPropertyMap = mutableMapOf<String, Any>()
    protected var entityManager: EntityManager? = null

    init {
        this.pageSize = pageSize
        this.name = name
        this.isSaveState = saveSate
    }

    override fun doOpen() {
        super.doOpen()
        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap)
        if (entityManager == null) {
            throw DataAccessResourceFailureException("Unable to obtain an EntityManager")
        }
    }

    override fun doReadPage() {
        val tx = getTxOrNull()

        val query: JPQLQuery<T> = createQuery()
            .offset(page.toLong() * pageSize)
            .limit(pageSize.toLong())

        initResults()

        fetchQuery(query, tx)
    }

    protected fun getTxOrNull(): EntityTransaction? {
        if (transacted) {
            val tx = entityManager!!.transaction
            tx.begin()

            entityManager!!.flush()
            entityManager!!.clear()
            return tx
        }

        return null
    }

    protected fun createQuery(): JPAQuery<T> {
        val queryFactory = JPAQueryFactory(entityManager)
        return queryFunction.apply(queryFactory)
    }

    protected fun initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = CopyOnWriteArrayList()
        } else {
            results.clear()
        }
    }

    protected fun fetchQuery(query: JPQLQuery<T>, tx: EntityTransaction?) {
        if (transacted) {
            results.addAll(query.fetch())
            tx?.commit()
        } else {
            val queryResult = query.fetch().toList()
            for (entity in queryResult) {
                entityManager!!.detach(entity)
                results.add(entity)
            }
        }
    }

    @Throws(Exception::class)
    override fun doClose() {
        entityManager!!.close()
        super.doClose()
    }
}