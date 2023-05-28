package com.dhkpo.proxy.jdkdynamic.code

import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class TimeInvocationHandler(private val target: Any) : InvocationHandler {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        log.info("TimeProxy 실행")
        val startTime = System.currentTimeMillis()

        val result: Any = args?.let {
            method.invoke(target, args)
        } ?: run  {
            method.invoke(target)
        }
        val endTime = System.currentTimeMillis()
        val resultTime = endTime - startTime
        log.info("TimeProxy 종료 resultTime={}", resultTime)
        return result
    }
}
