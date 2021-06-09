package com.example.lab1.dto

import com.example.lab1.entities.*
import java.sql.Date

data class OrderResponse(
        val id: Int,
        val date: Date,
        val status: String,
        val payment: String,
        val products: List<ProductResponse>,

        val isDelivery: Boolean,
        val address: String?, //если доставка
        val pickupPointId: Int? //если самовывоз
)

data class OrderForShopResponse(
        val id: Int,
        val products: List<ProductResponseForShop>
)

data class ProductResponseForShop(
        val product: SProductShortForShop,
        val quantity: Int
)

data class ProductResponse(
        val product: SProductShort,
        val quantity: Int
)

data class StatusRequest(
        val statusId: Int
)

object OrderResponseAssembler{
    fun buildDto(ent: Order) : OrderResponse{
        return OrderResponse(
                ent.id,
                ent.date,
                ent.status.name,
                ent.paymentMethod.name,
                products(ent.products),

                ent.isDelivery,
                ent.deliveryAddress ?: "",
                ent.pickupPoint?.id ?: -1
        )
    }

    private fun products(products: Collection<OrderProducts>) : List<ProductResponse>{
        return products.map { ProductResponse(
            SProductShortAssembler.buildDto(it.product),
            it.quantity
        ) }
    }
}

object OrderForShopResponseAssembler{
    fun buildDto(ent: Order) : OrderForShopResponse {
        return OrderForShopResponse(
                ent.id,
                products(ent.products)
        )
    }

    private fun products(products: Collection<OrderProducts>) : List<ProductResponseForShop> {
        return products.map {
            val product = SProductShortForShop(
                    it.id, it.product.product.name, it.orderStatus.name, it.product.cost
            )

            ProductResponseForShop(product, it.quantity)
        }
    }
}


data class OrderRequest (
            val products : List<BucketProductDTO>,
            val isDelivery : Boolean,
            val pickUpPointId : Int?,
            val deliveryAddress: String?,
            val paymentMethodId: Int
) {
    fun getEntity() : Order{
        return Order(
                isDelivery = isDelivery,
                pickupPoint = if (!isDelivery && pickUpPointId != null)
                    PickupPoint(pickUpPointId) else null,
                deliveryAddress = if (isDelivery && deliveryAddress != null)
                    deliveryAddress else null,
                paymentMethod = PaymentMethod(id = paymentMethodId),
                client = User()
        )
    }
}