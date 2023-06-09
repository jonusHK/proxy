package com.dhkpo.proxy.common.advice

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.slf4j.LoggerFactory

class TimeAdvice : MethodInterceptor {

    private val log = LoggerFactory.getLogger(this.javaClass)
    override fun invoke(invocation: MethodInvocation): Any? {
        log.info("TimeProxy 실행")
        val startTime = System.currentTimeMillis()

        // target 을 찾아서 args 를 다 넣어서 호출해줌
        val result: Any? = invocation.proceed()

        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        log.info("TimeProxy 종료 resultTime={}", resultTime)
        return result
    }
}
