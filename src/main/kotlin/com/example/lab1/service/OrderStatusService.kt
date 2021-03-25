package com.example.lab1.service

import com.example.lab1.entities.OrderStatusEntity

interface OrderStatusService {
    fun findAllStatuses() : List<OrderStatusEntity>

}