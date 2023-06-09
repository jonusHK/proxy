package com.dhkpo.proxy.proxyfactory

import com.dhkpo.proxy.common.advice.TimeAdvice
import com.dhkpo.proxy.common.service.ConcreteService
import com.dhkpo.proxy.common.service.ServiceImpl
import com.dhkpo.proxy.common.service.ServiceInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.ProxyFactory
import org.springframework.aop.support.AopUtils

class ProxyFactoryTest {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    fun interfaceProxy() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ServiceInterface

        log.info("targetClass={}", target.javaClass)
        log.info("proxyClass={}", proxy.javaClass)

        proxy.save()

        // 프록시 팩토리를 통해서 생성한 프록시에만 사용
        assertThat(AopUtils.isAopProxy(proxy)).isTrue()
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue()
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse()
    }

    @Test
    @DisplayName("구체 클래스만 있으면 JDK 동적 프록시 사용")
    fun concreteProxy() {
        val target = ConcreteService()
        val proxyFactory = ProxyFactory(target)

        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ConcreteService

        log.info("targetClass={}", target.javaClass)
        log.info("proxyClass={}", proxy.javaClass)

        proxy.call()

        // 프록시 팩토리를 통해서 생성한 프록시에만 사용
        assertThat(AopUtils.isAopProxy(proxy)).isTrue()
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse()
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue()
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB 를 사용하고, 클래스 기반 프록시 사용")
    fun proxyTargetClass() {
        val target = ServiceImpl()
        val proxyFactory = ProxyFactory(target)

        // 항상 CGLIB 기반으로 생성되도록 함 (인터페이스가 구현한 클래스라면 이 클래스를 상속하여 프록시로 생성함)
        proxyFactory.isProxyTargetClass = true

        proxyFactory.addAdvice(TimeAdvice())
        val proxy = proxyFactory.proxy as ServiceInterface

        log.info("targetClass={}", target.javaClass)
        log.info("proxyClass={}", proxy.javaClass)

        proxy.save()

        // 프록시 팩토리를 통해서 생성한 프록시에만 사용
        assertThat(AopUtils.isAopProxy(proxy)).isTrue()
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse()
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue()
    }
}
