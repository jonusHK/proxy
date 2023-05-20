package com.dhkpo.proxy.config.v1_proxy

import com.dhkpo.proxy.app.v1.*
import com.dhkpo.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy
import com.dhkpo.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy
import com.dhkpo.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InterfaceProxyConfig {

    @Bean
    fun orderControllerV1(logTrace: LogTrace): OrderControllerV1 {
        val controllerImpl = OrderControllerV1Impl(orderServiceV1(logTrace))
        return OrderControllerInterfaceProxy(controllerImpl, logTrace)
    }

    @Bean
    fun orderServiceV1(logTrace: LogTrace): OrderServiceV1 {
        val serviceImpl = OrderServiceV1Impl(orderRepositoryV1(logTrace))
        return OrderServiceInterfaceProxy(serviceImpl, logTrace)
    }

    @Bean
    fun orderRepositoryV1(logTrace: LogTrace): OrderRepositoryV1 {
        val repositoryImpl = OrderRepositoryV1Impl()
        return OrderRepositoryInterfaceProxy(repositoryImpl, logTrace)
    }
}