package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "order_products", schema = "s265098", catalog = "studs")
class OrderProductsEntity {
    @get:Column(name = "id")
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0

    @get:Column(name = "quantity")
    @get:Basic
    var quantity = 0

    @get:JoinColumn(name = "seller_product_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var sellerProductsBySellerProductId: SellerProductsEntity? = null

    @get:JoinColumn(name = "order_id", referencedColumnName = "id")
    @get:ManyToOne
    var ordersByOrderId: OrdersEntity? = null

    @get:JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var orderProductStatusByStatusId: OrderProductStatusEntity? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as OrderProductsEntity
        if (id != that.id) return false
        return quantity == that.quantity
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + quantity
        return result
    }
}