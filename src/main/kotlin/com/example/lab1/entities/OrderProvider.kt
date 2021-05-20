package com.example.lab1.entities

import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.OrderRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderProvider(
        private var orderRepo: OrderRepo
) {
    fun get(orderId: Int): Order {
        return orderRepo.findByIdOrNull(orderId) ?:
            throw ResourceNotFoundException("Order not found!")
    }

    fun getAllByUserId(userId: Int): List<Order> {
        return orderRepo.findAllByClientId(userId)
    }

    fun saveOrder(order: Order): Order {
        return orderRepo.save(order)
    }
}