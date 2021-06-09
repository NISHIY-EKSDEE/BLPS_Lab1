package com.example.lab1.entities

import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.OrderProductStatusRepo
import org.springframework.stereotype.Service

@Service
class OrderProductStatusProvider(
        private var orderProductStatusRepo: OrderProductStatusRepo
) {

    fun getByName(name: String) : OrderProductStatus {
        val status = orderProductStatusRepo.findByName(name)

        if (status != null) {
            return status
        }
        else {
            throw ResourceNotFoundException("STATUS not found!")
        }
    }
}