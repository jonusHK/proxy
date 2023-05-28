package com.dhkpo.proxy.jdkdynamic.code

import org.slf4j.LoggerFactory

class BImpl : BInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun call(): String {
        log.info("B 호출")
        return "b"
    }
}
