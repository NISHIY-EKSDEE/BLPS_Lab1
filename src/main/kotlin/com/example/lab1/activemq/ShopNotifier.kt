package com.example.lab1.activemq

import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class ShopNotifier(var jmsTemplate: JmsTemplate) {
    companion object {
        val queueName = "shopNotifications.queue"
    }

    fun notifyShop(message: String) {
        jmsTemplate.convertAndSend(queueName, message)
    }
}