package com.example.like.storage.db.core

import com.example.template.core.enums.ListOrder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.ComparableExpressionBase

fun <T : Comparable<T>> ComparableExpressionBase<T>.toOrder(listOrder: ListOrder): OrderSpecifier<T> {
    return if (listOrder == ListOrder.DESC) this.desc() else this.asc()
}
