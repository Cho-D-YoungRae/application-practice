package com.example.template.core.api.test

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

class TestCoreDatabase {

    private val data = CopyOnWriteArrayList<Object>()
    private val id = AtomicLong(0)

    fun generateId(): Long {
        return id.incrementAndGet()
    }

    fun add(item: Object) {
        data.add(item)
    }

    fun <T> getAll(type: Class<T>): List<T> {
        return data.filterIsInstance(type)
    }

    fun delete(item: Object) {
        data.remove(item)
    }

    fun clear() {
        data.clear()
        id.set(0)
    }
}