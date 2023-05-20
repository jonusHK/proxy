package com.dhkpo.proxy.config.v1_proxy.interface_proxy

import com.dhkpo.proxy.app.v1.OrderServiceV1
import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace

class OrderServiceInterfaceProxy(
    private val target: OrderServiceV1,
    private val logTrace: LogTrace
) : OrderServiceV1 {
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