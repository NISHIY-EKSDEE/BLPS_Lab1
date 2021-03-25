package com.example.lab1.entities

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "orders", schema = "s265098", catalog = "studs")
class OrdersEntity {
    @get:Column(name = "id")
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0

    @get:Column(name = "date")
    @get:Basic
    var date: Date? = null

    @get:Column(name = "is_delivery")
    @get:Basic
    var isDelivery = false

    @get:Column(name = "delivery_address")
    @get:Basic
    var deliveryAddress: String? = null

    @get:OneToMany(mappedBy = "ordersByOrderId")
    var orderProductsById: Collection<OrderProductsEntity>? = null

    @get:JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var usersByUserId: UsersEntity? = null

    @get:JoinColumn(name = "pickup_point_id", referencedColumnName = "id")
    @get:ManyToOne
    var pickupPointsByPickupPointId: PickupPointsEntity? = null

    @get:JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var orderStatusByStatusId: OrderStatusEntity? = null

    @get:JoinColumn(name = "payment_method_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var paymentMethodsByPaymentMethodId: PaymentMethodsEntity? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as OrdersEntity
        if (id != that.id) return false
        if (isDelivery != that.isDelivery) return false
        if (if (date != null) date != that.date else that.date != null) return false
        return !if (deliveryAddress != null) deliveryAddress != that.deliveryAddress else that.deliveryAddress != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (date != null) date.hashCode() else 0
        result = 31 * result + if (isDelivery) 1 else 0
        result = 31 * result + if (deliveryAddress != null) deliveryAddress.hashCode() else 0
        return result
    }
}