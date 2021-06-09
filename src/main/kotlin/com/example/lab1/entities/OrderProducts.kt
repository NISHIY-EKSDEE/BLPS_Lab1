package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "order_products")
class OrderProducts (
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Int = 0,

    @Column(name = "quantity")
    var quantity: Int = 0,

    @JoinColumn(name = "seller_product_id", referencedColumnName = "id")
    @ManyToOne
    var product: SellersProduct,

    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne
    var order: Order,

    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne
    var orderStatus: OrderProductStatus = OrderProductStatus()
)