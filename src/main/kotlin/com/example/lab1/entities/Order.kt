package com.example.lab1.entities

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Int = 0,

    @Column(name = "date")
    var date : Date = Date(System.currentTimeMillis()),

    @Column(name = "is_delivery")
    var isDelivery : Boolean = false,

    @Column(name = "delivery_address")
    var deliveryAddress: String? = null,

    @OneToMany(mappedBy = "order")
    var products: Collection<OrderProducts> = emptyList(),

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var client: User,

    @JoinColumn(name = "pickup_point_id", referencedColumnName = "id")
    @ManyToOne
    var pickupPoint: PickupPoint?,

    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne
    var status : OrderStatus = OrderStatus(id = 1),

    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    @ManyToOne
    var paymentMethod : PaymentMethod
)