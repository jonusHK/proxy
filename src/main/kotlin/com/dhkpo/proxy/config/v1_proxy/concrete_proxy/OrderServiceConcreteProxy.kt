package com.dhkpo.proxy.config.v1_proxy.concrete_proxy

import com.dhkpo.proxy.app.v2.OrderServiceV2
import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace

class OrderServiceConcreteProxy(
    private val target: OrderServiceV2,
    private val logTrace: LogTrace
) : OrderServiceV2(null) {

    override fun orderItem(itemId: String) {
        var status: TraceStatus? = null

        try {
            status = logTrace.begin("OrderService.orderItem()")
            // target 호출
            target.orderItem(itemId)
            logTrace.end(status)
        } catch (e: Exception) {
            status?.let { logTrace.exception(it, e) }
            throw e
        }
    }
}
