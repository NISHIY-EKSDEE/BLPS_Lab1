package com.example.lab1.service

import com.example.lab1.dto.OrderDTO
import com.example.lab1.entities.OrdersEntity
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min

@Validated
interface OrderService {
    fun findOrdersByUserId(@Min(value = 1L, message = "Invalid user ID.") id: Long) : List<OrderDTO>
    fun findOrderById(@Min(value = 1L, message = "Invalid product ID.") id: Long) : OrderDTO
    fun saveOrder(order: OrdersEntity) : OrderDTO
}