package com.dhkpo.proxy.config.v2_dynamicproxy.handler

import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.springframework.util.PatternMatchUtils
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class LogTraceFilterHandler(
    private val target: Any,
    private val logTrace: LogTrace,
    private val patterns: Array<String>
) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {

        // 메서드 이름 필터
        val methodName = method.name

        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return if (args != null) {
                method.invoke(target, *args)
            } else {
                method.invoke(target)
            }
        }

        var status: TraceStatus? = null

        try {
            val message = "${target::class.simpleName}.$methodName()"
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
