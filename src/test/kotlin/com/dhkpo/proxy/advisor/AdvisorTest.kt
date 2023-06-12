package com.dhkpo.proxy.advisor

import com.dhkpo.proxy.common.advice.TimeAdvice
import com.dhkpo.proxy.common.service.ServiceImpl
import com.dhkpo.proxy.common.service.ServiceInterface
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.aop.ClassFilter
import org.springframework.aop.MethodMatcher
import org.springframework.aop.Pointcut
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import java.lang.reflect.Method

class AdvisorTest {

    @Test
    fun advisorTest1() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)

        // 프록시 팩토리에 적용할 어드바이저 지정 (필수)
        val advisor = DefaultPointcutAdvisor(Pointcut.TRUE, TimeAdvice())
        proxyFactory.addAdvisor(advisor)

        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
        proxy.find()
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    fun advisorTest2() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)

        // 프록시 팩토리에 적용할 어드바이저 지정 (필수)
        val advisor = DefaultPointcutAdvisor(MyPointcut(), TimeAdvice())
        proxyFactory.addAdvisor(advisor)

        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
        proxy.find()
    }

    companion object {

        class MyPointcut : Pointcut {
            override fun getClassFilter(): ClassFilter {
                return ClassFilter.TRUE
            }

            override fun getMethodMatcher(): MethodMatcher {
                return MyMethodMatcher()
            }
        }

        class MyMethodMatcher : MethodMatcher {

            private val log = LoggerFactory.getLogger(MyMethodMatcher::class.java)

            private val matchName = "save"

            // isRuntime() 이 false 일 때 호출됨
            override fun matches(method: Method, targetClass: Class<*>): Boolean {
                val result = method.name.equals(matchName)
                log.info("포인트컷 호출 method={}, targetClass={}", method.name, targetClass)
                log.info("포인트컷 결과 result={}", result)
                return result
            }

            // false 인 경우 클래스의 정적 정보만 사용하므로 스프링이 내부에서 캐싱을 통해 성능 향상이 가능
            // true 인 경우 매개변수가 동적으로 변경된다고 가정하므로 캐싱을 하지 않음
            override fun isRuntime(): Boolean {
                return false
            }

            // isRuntime() 이 true 일 때 호출됨
            override fun matches(method: Method, targetClass: Class<*>, vararg args: Any?): Boolean {
                return false
            }
        }
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    fun advisorTest3() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)
        val pointcut = NameMatchMethodPointcut()

        // method 가 "save" 인 경우에만 처리
        pointcut.setMappedNames("save")

        // 프록시 팩토리에 적용할 어드바이저 지정 (필수)
        val advisor = DefaultPointcutAdvisor(pointcut, TimeAdvice())
        proxyFactory.addAdvisor(advisor)

        val proxy = proxyFactory.proxy as ServiceInterface

        proxy.save()
        proxy.find()
    }
}
