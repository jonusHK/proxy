package com.dhkpo.proxy

import com.dhkpo.proxy.config.v1_proxy.InterfaceProxyConfig
import com.dhkpo.proxy.trace.logtrace.LogTrace
import com.dhkpo.proxy.trace.logtrace.ThreadLocalLogTrace
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

//@Import(AppV1Config::class, AppV2Config::class)
@Import(InterfaceProxyConfig::class)
@SpringBootApplication(scanBasePackages = ["com.dhkpo.proxy.app"])
class ProxyApplication {

    @Bean
    fun logTrace(): LogTrace {
        return ThreadLocalLogTrace()
    }
}

fun main(args: Array<String>) {
    runApplication<ProxyApplication>(*args)
}
