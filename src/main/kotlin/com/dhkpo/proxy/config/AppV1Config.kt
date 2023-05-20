package com.dhkpo.proxy.config

import com.dhkpo.proxy.app.v1.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppV1Config {

    @Bean
    fun OrderControllerV1(): OrderControllerV1 {
        return OrderControllerV1Impl(OrderServiceV1())
    }

    @Bean
    fun OrderServiceV1(): OrderServiceV1 {
       return OrderServiceV1Impl(OrderRepositoryV1())
    }

    @Bean
    fun OrderRepositoryV1(): OrderRepositoryV1 {
        return OrderRepositoryV1Impl()
    }
}
