package com.dhkpo.proxy.config.v3_proxyfactory

import com.dhkpo.proxy.app.v2.OrderControllerV2
import com.dhkpo.proxy.app.v2.OrderRepositoryV2
import com.dhkpo.proxy.app.v2.OrderServiceV2
import com.dhkpo.proxy.config.v3_proxyfactory.advice.LogTraceAdvice
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.slf4j.LoggerFactory
import org.springframework.aop.Advisor
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProxyFactoryConfigV2 {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun orderControllerV2(logTrace: LogTrace): OrderControllerV2 {
        val orderController = OrderControllerV2(orderServiceV2(logTrace))
        val factory = ProxyFactory(orderController)
        factory.addAdvisor(getAdvisor(logTrace))
        val proxy = factory.proxy as OrderControllerV2
        log.info("ProxyFactory proxy={}, target={}", proxy.javaClass, orderController.javaClass)
        return proxy
    }

    @Bean
    fun orderServiceV2(logTrace: LogTrace): OrderServiceV2 {
        val orderService = OrderServiceV2(orderRepositoryV2(logTrace))
        val factory = ProxyFactory(orderService)
        factory.addAdvisor(getAdvisor(logTrace))
        val proxy = factory.proxy as OrderServiceV2
        log.info("ProxyFactory proxy={}, target={}", proxy.javaClass, orderService.javaClass)
        return proxy
    }

    @Bean
    fun orderRepositoryV2(logTrace: LogTrace): OrderRepositoryV2 {
        val orderRepository = OrderRepositoryV2()
        val factory = ProxyFactory(orderRepository)
        factory.addAdvisor(getAdvisor(logTrace))
        val proxy = factory.proxy as OrderRepositoryV2
        log.info("ProxyFactory proxy={}, target={}", proxy.javaClass, orderRepository.javaClass)
        return proxy

    }

    private fun getAdvisor(logTrace: LogTrace): Advisor {
        // pointcut
        val pointcut = NameMatchMethodPointcut()
        pointcut.setMappedNames("request*", "order*", "save*")

        // advice
        val advice = LogTraceAdvice(logTrace)
        return DefaultPointcutAdvisor(pointcut, advice)
    }
}