package com.example.lab1.entities

import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.OrderProductRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderProductsProvider(
        private var orderProductsRepo: OrderProductRepo
) {
    fun save(orderProducts: OrderProducts): OrderProducts {
        return orderProductsRepo.save(orderProducts)
    }

    fun getById(prodId: Int): OrderProducts {
        val product = orderProductsRepo.findByIdOrNull(prodId)
        if (product == null) {
            throw ResourceNotFoundException("Product not found!")
        }
        else {
            return product
        }
    }
}