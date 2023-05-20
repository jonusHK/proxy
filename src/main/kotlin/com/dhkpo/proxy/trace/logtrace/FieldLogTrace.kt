package com.dhkpo.proxy.trace.logtrace

import com.dhkpo.proxy.trace.TraceId
import com.dhkpo.proxy.trace.TraceStatus
import org.slf4j.LoggerFactory

class FieldLogTrace: LogTrace {

    private val log = LoggerFactory.getLogger(this.javaClass)

    companion object {
        @JvmStatic
        private val START_PREFIX = "-->"

        @JvmStatic
        private val COMPLETE_PREFIX = "<--"

        @JvmStatic
        private val EX_PREFIX = "<X-"
    }

    private var traceIdHolder: TraceId? = null // traceId 동기화, 동시성 이슈 발생

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId: TraceId = traceIdHolder!!
        val startTimeMs: Long = System.currentTimeMillis()
        log.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        traceIdHolder = if (traceIdHolder != null) traceIdHolder!!.createNextId() else TraceId()
    }

    override fun end(status: TraceStatus) {
        complete(status, null)
    }

    override fun exception(status: TraceStatus, e: Exception) {
        complete(status, e)
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs = stopTimeMs - status.startTimeMs
        val traceId = status.traceId

        when (e) {
            null -> log.info("[{}] {}{} time={}ms", traceId.id, addSpace(COMPLETE_PREFIX, traceId.level), status.message, resultTimeMs)
            else -> log.info("[{}] {}{} time={}ms ex={}", traceId.id, addSpace(EX_PREFIX, traceId.level), status.message, resultTimeMs, e.toString())
        }
        releaseTraceId()
    }

    private fun releaseTraceId() {
        traceIdHolder?.let {
            traceIdHolder = if (it.isFirstLevel()) null else it.createPreviousId()
        }
    }

    private fun addSpace(prefix: String, level: Int): String {
        val sb = StringBuilder()
        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|$prefix" else "|   ")
        }
        return sb.toString()
    }
}