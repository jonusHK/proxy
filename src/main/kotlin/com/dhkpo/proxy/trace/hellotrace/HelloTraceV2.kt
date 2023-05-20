package com.dhkpo.proxy.trace.hellotrace

import com.dhkpo.proxy.trace.TraceId
import com.dhkpo.proxy.trace.TraceStatus
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class HelloTraceV2 {

    private val log = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @JvmStatic
        private val START_PREFIX = "-->"

        @JvmStatic
        private val COMPLETE_PREFIX = "<--"

        @JvmStatic
        private val EX_PREFIX = "<X-"
    }


    fun begin(message: String): TraceStatus {
        val traceId = TraceId()
        val startTimeMs: Long = System.currentTimeMillis()
        log.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)
        return TraceStatus(traceId, startTimeMs, message)
    }

    fun beginSync(beforeTraceId: TraceId, message: String): TraceStatus {
        val nextId: TraceId = beforeTraceId.createNextId()
        val startTimeMs: Long = System.currentTimeMillis()
        log.info("[{}] {}{}", nextId.id, addSpace(START_PREFIX, nextId.level), message)
        return TraceStatus(nextId, startTimeMs, message)
    }

    fun end(status: TraceStatus) {
        complete(status, null)
    }

    fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
    }

    fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs = stopTimeMs - status.startTimeMs
        val traceId = status.traceId

        when (e) {
            null -> log.info("[{}] {}{} time={}ms", traceId.id, addSpace(COMPLETE_PREFIX, traceId.level), status.message, resultTimeMs)
            else -> log.info("[{}] {}{} time={}ms ex={}", traceId.id, addSpace(EX_PREFIX, traceId.level), status.message, resultTimeMs, e.toString())
        }
    }

    fun addSpace(prefix: String, level: Int): String {
        val sb = StringBuilder()
        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|$prefix" else "|   ")
        }
        return sb.toString()
    }
}
