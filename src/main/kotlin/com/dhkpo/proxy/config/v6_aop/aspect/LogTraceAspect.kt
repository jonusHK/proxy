package com.dhkpo.proxy.config.v6_aop.aspect

import com.dhkpo.proxy.trace.TraceStatus
import com.dhkpo.proxy.trace.logtrace.LogTrace
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory

// 어드바이저
@Aspect
class LogTraceAspect(private val logTrace: LogTrace) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Around("execution(* com.dhkpo.proxy.app..*(..))") // 포인트컷
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        // 어드바이스

        var status: TraceStatus? = null

        try {
            // joinPoint.target (실제 호출 대상)
            // joinPoint.args (전달 인자)
            val message = joinPoint.signature.toShortString()
            status = logTrace.begin(message)

            val result = joinPoint.proceed()

            logTrace.end(status)
            return result
        } catch (e: Exception) {
            status?.let { logTrace.exception(it, e) }
            throw e
        }
    }
}
