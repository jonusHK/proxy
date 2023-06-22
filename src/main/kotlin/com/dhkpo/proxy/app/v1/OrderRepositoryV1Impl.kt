package com.dhkpo.proxy.app.v1

open class OrderRepositoryV1Impl : OrderRepositoryV1 {
    override fun save(itemId: String) {
        if (itemId == "ex") throw IllegalStateException("예외 발생!")
        sleep(1000)
    }

    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}