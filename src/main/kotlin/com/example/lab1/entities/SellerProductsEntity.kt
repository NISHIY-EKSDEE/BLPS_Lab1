package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "seller_products", schema = "s265098", catalog = "studs")
class SellerProductsEntity {
    @get:Column(name = "id")
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Int = 0

    @get:Column(name = "cost")
    @get:Basic
    var cost: Int? = null

    @get:Column(name = "total_amount")
    @get:Basic
    var totalAmount = 0

    @get:OneToMany(mappedBy = "sellerProductsBySellerProductId")
    var orderProductsById: Collection<OrderProductsEntity>? = null

    @get:JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var productsByProductId: ProductsEntity? = null

    @get:JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var sellersBySellerId: SellersEntity? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as SellerProductsEntity
        if (id != that.id) return false
        if (totalAmount != that.totalAmount) return false
        return !if (cost != null) cost != that.cost else that.cost != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (cost != null) cost.hashCode() else 0
        result = 31 * result + totalAmount
        return result
    }
}