package com.example.lab1.repo
import com.example.lab1.entities.OrderStatusEntity
import org.springframework.data.repository.CrudRepository

interface OrderStatusRepo : CrudRepository<OrderStatusEntity, Long> {
}