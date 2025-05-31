package com.example.like.storage.db.core

import com.example.template.core.enums.ListOrder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.ComparableExpression

fun <T : Comparable<T>> ComparableExpression<T>.toOrder(listOrder: ListOrder): OrderSpecifier<T> {
    return if (listOrder == ListOrder.DESC) this.desc() else this.asc()
}
