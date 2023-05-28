package com.dhkpo.proxy.cglib

import com.dhkpo.proxy.cglib.code.TimeMethodInterceptor
import com.dhkpo.proxy.common.service.ConcreteService
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.cglib.proxy.Enhancer

class CglibTest {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun cglib() {
        val target = ConcreteService()

        val enhancer = Enhancer()
        enhancer.setSuperclass(ConcreteService::class.java)
        enhancer.setCallback(TimeMethodInterceptor(target))
        val proxy = enhancer.create() as ConcreteService
        log.info("targetClass={}", target.javaClass)
        log.info("proxyClass={}", proxy.javaClass)

        proxy.call()
    }
}
