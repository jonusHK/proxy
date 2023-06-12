package com.dhkpo.proxy.postprocessor

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BasicTest {

    @Test
    fun basicConfig() {
        val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(BasicConfig::class.java)

        // A는 빈으로 등록된다.
        val a = applicationContext.getBean("beanA", A::class) as A
        a.helloA()

        // B는 빈으로 등록되지 않는다.
        Assertions.assertThrows(NoSuchBeanDefinitionException::class.java) {
            applicationContext.getBean(B::class.java)
        }
    }

    companion object {

        @Configuration
        class BasicConfig {
            @Bean(name = ["beanA"])
            fun a(): A {
                return A()
            }
        }

        class A {
            private val log = LoggerFactory.getLogger(this.javaClass)

            fun helloA() {
                log.info("hello A")
            }
        }

        class B {
            private val log = LoggerFactory.getLogger(this.javaClass)

            fun helloB() {
                log.info("hello B")
            }
        }
    }
}