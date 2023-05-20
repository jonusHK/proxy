package com.dhkpo.proxy.app.v1

class OrderServiceV1Impl(private val proxyOrderRepositoryV1: OrderRepositoryV1) : OrderServiceV1 {

    override fun orderItem(itemId: String) {
        proxyOrderRepositoryV1.save(itemId)
    }
}