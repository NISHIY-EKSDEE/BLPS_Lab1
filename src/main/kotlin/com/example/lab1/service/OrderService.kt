package com.example.lab1.service

import com.example.lab1.dto.OrderDTO
import com.example.lab1.entities.OrderEntity
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min

@Validated
interface OrderService {
    fun findOrdersByUserId(@Min(value = 1L, message = "Invalid user ID.") id: Long) : List<OrderDTO>
    fun findOrderById(@Min(value = 1, message = "Invalid product ID.") id: Int) : OrderDTO
    fun findOrderByIdRetEnt(id: Int): OrderEntity
    fun saveOrderEntity(order: OrderEntity) : OrderDTO
    fun saveOrderEntityAndRetEntity(order: OrderEntity) : OrderEntity
    fun deleteById(id: Int)
}