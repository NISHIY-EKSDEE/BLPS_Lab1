package com.example.lab1.jobs

import com.example.lab1.entities.OrderProductStatusProvider
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.example.lab1.service.OrderService

@Component
class CheckOrdersJob : Job {

    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var orderProductStatusProvider: OrderProductStatusProvider

    override fun execute(p0: JobExecutionContext) {
        val unconfirmedOrders = orderService.getAllUnconfirmedOrders()
        val confirmedOrderProductStatus = orderProductStatusProvider.getByName("Confirmed")
        for (order in unconfirmedOrders) {
            println("[CHECK JOB LOG] Checking products for order #${order.id} with status ${order.status.name}")
            if (order.products.all { it.orderStatus == confirmedOrderProductStatus }) {
                println("\tAll order products were confirmed, changing order status to Confirmed")
                orderService.changeStatus(order.id, 2)
            } else {
                println("\tUnconfirmed products were found, status won't be changed")
            }
        }
    }
}