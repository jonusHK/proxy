package com.dhkpo.proxy.trace

import java.util.*


class TraceId {

    var id: String
    var level: Int

    constructor() {
        this.id = createId()
        this.level = 0
    }

    constructor(id: String, level: Int) {
        this.id = id
        this.level = level
    }

    private fun createId(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    fun createNextId(): TraceId {
        return TraceId(id, level + 1)
    }

    fun createPreviousId(): TraceId {
        return TraceId(id, level - 1)
    }

    fun isFirstLevel(): Boolean {
        return level == 0
    }
}
