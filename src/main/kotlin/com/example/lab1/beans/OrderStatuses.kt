package com.example.lab1.beans

import com.example.lab1.entities.OrderStatusEntity
import com.example.lab1.service.OrderStatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
object OrderStatuses{

    @Autowired
    private lateinit var orderStatusService: OrderStatusService

    private var isListSet : Boolean = false

    var list : List<OrderStatusEntity>? = null
        get() {
            return if (isListSet) field
            else {
                field = orderStatusService.findAllStatuses()
                isListSet = true
                field
            }
        }


}