package com.dhkpo.proxy.config.v5_autoproxy


import com.dhkpo.proxy.config.AppV1Config
import com.dhkpo.proxy.config.AppV2Config
import com.dhkpo.proxy.config.v3_proxyfactory.advice.LogTraceAdvice
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.springframework.aop.Advisor
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AppV1Config::class, AppV2Config::class)
class AutoProxyConfig {

    @Bean
    fun advisor1(logTrace: LogTrace): Advisor {

        // pointcut
        val pointcut = NameMatchMethodPointcut()
        pointcut.setMappedNames("request*", "order*", "save*")

        // advice
        val advice = LogTraceAdvice(logTrace)
        return DefaultPointcutAdvisor(pointcut, advice)
    }
}
