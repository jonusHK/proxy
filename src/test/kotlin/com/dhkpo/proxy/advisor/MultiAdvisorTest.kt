package com.dhkpo.proxy.advisor

import com.dhkpo.proxy.common.service.ServiceImpl
import com.dhkpo.proxy.common.service.ServiceInterface
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.aop.Pointcut
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor

class MultiAdvisorTest {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    @DisplayName("여러 프록시")
    fun multiAdvisorTest1() {
        // client -> proxy2(advisor2) -> proxy1(advisor1) -> target

        // 프록시1 생성
        val target = ServiceImpl()
        val proxyFactory1 = ProxyFactory(target)
        val advisor1 = DefaultPointcutAdvisor(Pointcut.TRUE, object: MethodInterceptor {

            override fun invoke(invocation: MethodInvocation): Any? {
                log.info("advice1 호출")
                return invocation.proceed()
            }
        })
        proxyFactory1.addAdvisor(advisor1)
        val proxy1 = proxyFactory1.proxy as ServiceInterface

        // 프록시2 생성
        val proxyFactory2 = ProxyFactory(proxy1)
        val advisor2 = DefaultPointcutAdvisor(Pointcut.TRUE, object: MethodInterceptor {

            override fun invoke(invocation: MethodInvocation): Any? {
                log.info("advice2 호출")
                return invocation.proceed()
            }
        })
        proxyFactory2.addAdvisor(advisor2)
        val proxy2 = proxyFactory2.proxy as ServiceInterface

        // 실행
        proxy2.save()
    }

    @Test
    @DisplayName("하나의 프록시, 여러 어드바이저")
    fun multiAdvisorTest2() {
        // client -> proxy -> advisor2 -> advisor1 -> target

        // 프록시1 생성
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)
        val advisor1 = DefaultPointcutAdvisor(Pointcut.TRUE, object: MethodInterceptor {

            override fun invoke(invocation: MethodInvocation): Any? {
                log.info("advice1 호출")
                return invocation.proceed()
            }
        })
        val advisor2 = DefaultPointcutAdvisor(Pointcut.TRUE, object: MethodInterceptor {

            override fun invoke(invocation: MethodInvocation): Any? {
                log.info("advice2 호출")
                return invocation.proceed()
            }
        })

        // 등록하는 순서대로 호출됨 (advisor2 -> advisor1)
        proxyFactory.addAdvisor(advisor2)
        proxyFactory.addAdvisor(advisor1)

        // 프록시는 하나만 만든다.
        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
    }
}
