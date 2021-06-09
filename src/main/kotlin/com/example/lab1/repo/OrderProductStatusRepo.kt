package com.example.lab1.repo

import com.example.lab1.entities.OrderProductStatus
import org.springframework.data.jpa.repository.JpaRepository

interface OrderProductStatusRepo : JpaRepository<OrderProductStatus, Int> {
    fun findByName(name: String) : OrderProductStatus?
}