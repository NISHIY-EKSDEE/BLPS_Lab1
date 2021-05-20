package com.example.lab1.entities

import com.example.lab1.repo.OrderProductRepo
import org.springframework.stereotype.Repository

@Repository
class OrderProductsProvider(
        private var orderProductsRepo: OrderProductRepo
) {
    fun save(orderProducts: OrderProducts): OrderProducts {
        return orderProductsRepo.save(orderProducts)
    }
}