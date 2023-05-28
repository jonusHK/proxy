package com.dhkpo.proxy.common.service

import org.slf4j.LoggerFactory

open class ConcreteService {

    private val log = LoggerFactory.getLogger(this.javaClass)

    open fun call() {
        log.info("ConcreteService 호출")
    }
}
