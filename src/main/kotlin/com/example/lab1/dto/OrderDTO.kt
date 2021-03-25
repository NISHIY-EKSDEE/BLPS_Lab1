package com.example.lab1.dto

import com.example.lab1.entities.OrderProductStatusEntity
import com.example.lab1.entities.OrderProductsEntity
import com.example.lab1.entities.OrdersEntity
import com.example.lab1.entities.PickupPointsEntity
import java.util.*

data class OrderDTO(
        val id: Int,
        val date: java.sql.Date,
        val isDelivery: Boolean,
        val pickupPointId: Int?,
        val pickupPointAddress: String?,
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

object OrderAssembler{
    fun buildDto(ent: OrdersEntity) : OrderDTO {
        return OrderDTO(
                ent.id,
                ent.date!!,
                ent.isDelivery,
                ent.pickupPointsByPickupPointId?.id,
                ent.pickupPointsByPickupPointId?.address,
                ent.orderStatusByStatusId?.name!!,
                ent.paymentMethodsByPaymentMethodId?.name!!,
                ent.orderProductsById?.map(OrderProductAssembler::buildDTO)?.toList()!!
        )
    }
}

object OrderProductAssembler{
    fun buildDTO(ent: OrderProductsEntity) : OrderProductDTO {
        return OrderProductDTO(
                ent.id,
                SellerProductAssembler.buildShortDto(ent.sellerProductsBySellerProductId!!),
                ent.ordersByOrderId?.id!!,
                ent.quantity,
                ent.orderProductStatusByStatusId?.name!!
        )
    }
}