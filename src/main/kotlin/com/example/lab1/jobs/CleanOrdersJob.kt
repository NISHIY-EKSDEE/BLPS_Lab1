package com.example.lab1.jobs

import com.example.lab1.service.OrderService
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component;
import java.time.LocalDate
import java.time.Period

@Component
class CleanOrdersJob : Job {

    @Autowired
    lateinit var orderService: OrderService

    override fun execute(p0: JobExecutionContext) {
        val unconfirmedOrders = orderService.getAllUnconfirmedOrders()
        val currentDate = LocalDate.now()

        for (order in unconfirmedOrders) {
            println("[CLEAN JOB LOG] Checking order #${order.id} if it wasn't confirmed for 1 week")
            if (Period.between(order.date.toLocalDate(), currentDate).days >= 7) {
                println("\tDifference between today and order date is 7 days or more, cancelling it")
                orderService.changeStatus(order.id, 5)
            } else {
                println("\tOrder isn't old enough")
            }
        }
    }
}