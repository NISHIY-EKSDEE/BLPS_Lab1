package com.example.lab1.activemq

import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate


@Configuration
class JmsConfig {
    private var BROKER_URL = "tcp://localhost:61616"
    private var BROKER_USERNAME = "admin"
    private var BROKER_PASSWORD = "admin"

    @Bean
    fun connectionFactory(): ActiveMQConnectionFactory {
        val connectionFactory = ActiveMQConnectionFactory()
        connectionFactory.brokerURL = BROKER_URL
        connectionFactory.password = BROKER_USERNAME
        connectionFactory.userName = BROKER_PASSWORD
        return connectionFactory
    }

    @Bean
    fun jmsTemplate(): JmsTemplate {
        val template = JmsTemplate()
        template.connectionFactory = connectionFactory()
        return template
    }

    @Bean
    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory())
        factory.setConcurrency("1-1")
        return factory
    }
}
