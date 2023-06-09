package com.dhkpo.proxy.common.service

import org.slf4j.LoggerFactory

open class ServiceImpl : ServiceInterface {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun save() {
        log.info("save 호출")
    }

    override fun find() {
        log.info("find 호출")
    }
}
