package com.dhkpo.proxy.pureproxy.decorator.code

import org.slf4j.LoggerFactory

class RealComponent : Component {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun operation(): String {
        log.info("RealComponent 실행")
        return "data"
    }
}