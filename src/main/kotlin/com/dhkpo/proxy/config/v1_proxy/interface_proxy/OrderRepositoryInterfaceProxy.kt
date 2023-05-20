package com.dhkpo.proxy.config.v1_proxy.interface_proxy

import com.dhkpo.proxy.app.v1.OrderRepositoryV1
import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace

class OrderRepositoryInterfaceProxy(
    private val target: OrderRepositoryV1,
    private val logTrace: LogTrace
) : OrderRepositoryV1 {
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