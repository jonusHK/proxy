package com.dhkpo.proxy.app.v1

class OrderControllerV1Impl(private val proxyOrderServiceV1: OrderServiceV1) : OrderControllerV1 {

    override fun request(itemId: String): String {
        proxyOrderServiceV1.orderItem(itemId)
        return "ok"
    }

    override fun noLog(): String {
        return "ok"
    }
}
