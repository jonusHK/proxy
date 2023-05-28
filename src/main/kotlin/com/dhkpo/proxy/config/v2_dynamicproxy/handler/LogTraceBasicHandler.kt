package com.dhkpo.proxy.config.v2_dynamicproxy.handler

import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class LogTraceBasicHandler(
    private val target: Any,
    private val logTrace: LogTrace
) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
        var status: TraceStatus? = null

        try {
            val message = "${target::class.simpleName}.${method.name}()"
            status = logTrace.begin(message)

            // target 호출
            val result: Any? = if (args != null) {
                method.invoke(target, *args)
            } else {
                method.invoke(target)
            }

            logTrace.end(status)
            return result
        } catch (e: Exception) {
            status?.let { logTrace.exception(it, e) }
            throw e
        }
    }
}
