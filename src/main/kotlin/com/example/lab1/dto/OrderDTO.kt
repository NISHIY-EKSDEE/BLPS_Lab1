package com.example.lab1.dto

import com.example.lab1.entities.OrderProductStatusEntity
import java.util.*

data class OrderDTO(
        val id: Int,
        val date: Date,
        val isDelivery: Boolean,
        val pickupPointId: Int,
        val pickupPointName: String,
        val status: String,
        val payment: String,
        val products: List<OrderProductDTO>
)

data class OrderProductDTO(
        val id: Int,
        val product: SellerProductShortDTO,
        var orderId: Int,
        val quantity: Int,
        val status: String
)