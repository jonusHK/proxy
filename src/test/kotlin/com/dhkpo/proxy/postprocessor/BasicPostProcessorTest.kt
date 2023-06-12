package com.dhkpo.proxy.postprocessor

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BasicPostProcessorTest {

    @Test
    fun basicConfig() {
        val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(BeanPostProcessorConfig::class.java)

        // B는 빈으로 등록된다.
        val b = applicationContext.getBean("beanA", B::class) as B
        b.helloB()

        // A는 빈으로 등록되지 않는다.
        Assertions.assertThrows(NoSuchBeanDefinitionException::class.java) {
            applicationContext.getBean(A::class.java)
        }
    }

    companion object {

        @Configuration
        class BeanPostProcessorConfig {
            @Bean(name = ["beanA"])
            fun a(): A {
                return A()
            }

            @Bean
            fun aToBPostProcessor(): AToBPostProcessor {
                return AToBPostProcessor()
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

        class AToBPostProcessor : BeanPostProcessor {

            private val log = LoggerFactory.getLogger(this.javaClass)

            override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
                log.info("beanName={} bean={}", beanName, bean)

                if (bean is A) {
                    return B()
                }
                return bean
            }
        }
    }
}
