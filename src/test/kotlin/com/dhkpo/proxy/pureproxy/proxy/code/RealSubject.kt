package com.dhkpo.proxy.pureproxy.proxy.code

import org.slf4j.LoggerFactory

class RealSubject : Subject {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun operation(): String {
        log.info("실제 객체 호출")
        sleep(1000)
        return "data"
    }

    private fun sleep(millis: Int) {
        try {
            Thread.sleep(millis.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
