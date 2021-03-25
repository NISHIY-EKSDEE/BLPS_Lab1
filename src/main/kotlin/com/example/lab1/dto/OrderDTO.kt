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
        val pickupPointAddress: String,
        val status: String?,
        val payment: String?,
        val products: List<OrderProductDTO>
)

data class OrderProductDTO(
        val product: SellerProductShortDTO,
        var orderId: Int,
        val quantity: Int,
        val status: String?
)

data class OrderShortDTO(
        val id: Int,
        val date: java.sql.Date,
        val isDelivery: Boolean,
        val pickupPointId: Int?,
        val products: List<OrderProductShortDTO>
)

data class OrderProductShortDTO(
        val sellerProductId: Int,
        var orderId: Int,
        val quantity: Int
)

object OrderAssembler{
    fun buildDto(ent: OrdersEntity) : OrderDTO {
        return OrderDTO(
                ent.id,
                ent.date!!,
                ent.isDelivery,
                ent.pickupPointsByPickupPointId?.id,
                ent.pickupPointsByPickupPointId!!.address!!,
                ent.orderStatusByStatusId?.name,
                ent.paymentMethodsByPaymentMethodId?.name,
                ent.orderProductsById?.map(OrderProductAssembler::buildDTO)?.toList()!!
        )
    }

    fun buildShortDto(ent: OrdersEntity) : OrderShortDTO {
        return OrderShortDTO(
                ent.id,
                ent.date!!,
                ent.isDelivery,
                ent.pickupPointsByPickupPointId?.id,
                ent.orderProductsById?.map {
                    OrderProductShortDTO(
                            it.sellerProductsBySellerProductId!!.id,
                            it.ordersByOrderId!!.id,
                            it.quantity
                    )
                }!!.toList()
        )
    }
}

object OrderProductAssembler{
    fun buildDTO(ent: OrderProductsEntity) : OrderProductDTO {
        return OrderProductDTO(
                SellerProductAssembler.buildShortDto(ent.sellerProductsBySellerProductId!!),
                ent.ordersByOrderId?.id!!,
                ent.quantity,
                ent.orderProductStatusByStatusId?.name
        )
    }
}