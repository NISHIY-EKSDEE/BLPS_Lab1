package com.example.lab1.repo

import com.example.lab1.entities.Order
import com.example.lab1.entities.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepo: JpaRepository<Order, Int> {
    fun findAllByClientId(clientId: Int) : List<Order>
    fun findAllByStatusEquals(status: OrderStatus) : List<Order>
}