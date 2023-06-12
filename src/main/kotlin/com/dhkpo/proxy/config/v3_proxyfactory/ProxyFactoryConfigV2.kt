package com.dhkpo.proxy.config.v3_proxyfactory

import com.dhkpo.proxy.app.v1.*
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
class ProxyFactoryConfigV1 {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun orderControllerV1(logTrace: LogTrace): OrderControllerV1 {
        val orderController = OrderControllerV1Impl(orderServiceV1(logTrace))
        val factory = ProxyFactory(orderController)
        factory.addAdvisor(getAdvisor(logTrace))
        val proxy = factory.proxy as OrderControllerV1
        log.info("ProxyFactory proxy={}, target={}", proxy.javaClass, orderController.javaClass)
        return proxy
    }

    @Bean
    fun orderServiceV1(logTrace: LogTrace): OrderServiceV1 {
        val orderService = OrderServiceV1Impl(orderRepositoryV1(logTrace))
        val factory = ProxyFactory(orderService)
        factory.addAdvisor(getAdvisor(logTrace))
        val proxy = factory.proxy as OrderServiceV1
        log.info("ProxyFactory proxy={}, target={}", proxy.javaClass, orderService.javaClass)
        return proxy
    }

    @Bean
    fun orderRepositoryV1(logTrace: LogTrace): OrderRepositoryV1 {
        val orderRepository = OrderRepositoryV1Impl()
        val factory = ProxyFactory(orderRepository)
        factory.addAdvisor(getAdvisor(logTrace))
        val proxy = factory.proxy as OrderRepositoryV1
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