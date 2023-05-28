package com.dhkpo.proxy.config.v2_dynamicproxy

import com.dhkpo.proxy.app.v1.*
import com.dhkpo.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Proxy

@Configuration
class DynamicProxyFilterConfig {

    companion object {
        private val PATTERNS = arrayOf("request*", "order*", "save*")
    }

    @Bean
    fun orderControllerV1(logTrace: LogTrace): OrderControllerV1 {
        val target = OrderControllerV1Impl(orderServiceV1(logTrace))

        return (Proxy.newProxyInstance(
            OrderControllerV1::class.java.classLoader,
            arrayOf(OrderControllerV1::class.java),
            LogTraceFilterHandler(target, logTrace, PATTERNS)
        )) as OrderControllerV1
    }

    @Bean
    fun orderServiceV1(logTrace: LogTrace): OrderServiceV1 {
        val target = OrderServiceV1Impl(orderRepositoryV1(logTrace))

        return (Proxy.newProxyInstance(
            OrderServiceV1::class.java.classLoader,
            arrayOf(OrderServiceV1::class.java),
            LogTraceFilterHandler(target, logTrace, PATTERNS)
        )) as OrderServiceV1
    }

    @Bean
    fun orderRepositoryV1(logTrace: LogTrace): OrderRepositoryV1 {
        val target = OrderRepositoryV1Impl()

        return (Proxy.newProxyInstance(
            OrderRepositoryV1::class.java.classLoader,
            arrayOf(OrderRepositoryV1::class.java),
            LogTraceFilterHandler(target, logTrace, PATTERNS)
        )) as OrderRepositoryV1
    }
}
