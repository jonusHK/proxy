package com.dhkpo.proxy.config.v6_aop

import com.dhkpo.proxy.config.AppV1Config
import com.dhkpo.proxy.config.AppV2Config
import com.dhkpo.proxy.config.v6_aop.aspect.LogTraceAspect
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AppV1Config::class, AppV2Config::class)
class AopConfig {

    @Bean
    fun logTraceAspect(logTrace: LogTrace): LogTraceAspect {
        return LogTraceAspect(logTrace)
    }
}
