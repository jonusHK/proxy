package com.dhkpo.proxy.config.v1_proxy.concrete_proxy

import com.dhkpo.proxy.app.v2.OrderRepositoryV2
import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace

class OrderRepositoryConcreteProxy(
    private val target: OrderRepositoryV2,
    private val logTrace: LogTrace
) : OrderRepositoryV2() {

    override fun save(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = logTrace.begin("OrderRepository.request()")
            // target 호출
            target.save(itemId)
            logTrace.end(status)
        } catch (e: Exception) {
            status?.let { logTrace.exception(it, e) }
            throw e
        }
    }
}