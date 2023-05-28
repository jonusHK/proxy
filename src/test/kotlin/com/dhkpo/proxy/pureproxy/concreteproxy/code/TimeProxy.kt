package com.dhkpo.proxy.pureproxy.concreteproxy.code

import org.slf4j.LoggerFactory

class TimeProxy(private val concreteLogic: ConcreteLogic) : ConcreteLogic() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun operation(): String {
        log.info("TimeDecorator 실행")
        val startTime = System.currentTimeMillis()

        val result: String = concreteLogic.operation()

        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime)
        return result
    }
}
