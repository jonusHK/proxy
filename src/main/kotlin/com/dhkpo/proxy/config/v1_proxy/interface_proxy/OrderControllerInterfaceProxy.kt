package com.dhkpo.proxy.config.v1_proxy.interface_proxy

import com.dhkpo.proxy.app.v1.OrderControllerV1
import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace

class OrderControllerInterfaceProxy(
    private val target: OrderControllerV1,
    private val logTrace: LogTrace
) : OrderControllerV1 {
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
