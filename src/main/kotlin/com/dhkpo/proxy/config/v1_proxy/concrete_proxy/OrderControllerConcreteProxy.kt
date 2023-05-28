package com.dhkpo.proxy.config.v1_proxy.concrete_proxy

import com.dhkpo.proxy.app.v2.OrderControllerV2
import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace

class OrderControllerConcreteProxy(
    private val target: OrderControllerV2,
    private val logTrace: LogTrace
) : OrderControllerV2(null) {
    override fun request(itemId: String): String {
        var status: TraceStatus? = null

        try {
            status = logTrace.begin("OrderService.orderItem()")
            // target 호출
            val result = target.request(itemId)
            logTrace.end(status)
            return result
        } catch (e: Exception) {
            status?.let { logTrace.exception(it, e) }
            throw e
        }
    }

    override fun noLog(): String {
        return target.noLog()
    }
}