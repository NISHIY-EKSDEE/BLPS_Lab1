package com.example.lab1.repo
import com.example.lab1.entities.OrderStatus
import org.springframework.data.repository.CrudRepository

interface OrderStatusRepo : CrudRepository<OrderStatus, Int> {
}