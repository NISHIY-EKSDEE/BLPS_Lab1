package com.example.lab1.entities

import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.OrderStatusRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderStatusProvider(
        private var orderStatusRepo: OrderStatusRepo
) {
    fun get(orderStatusId: Int): OrderStatus {
        return orderStatusRepo.findByIdOrNull(orderStatusId) ?:
            throw ResourceNotFoundException("This status doesn't exist")
    }
}