package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "order_product_status", schema = "s265098", catalog = "studs")
class OrderProductStatusEntity {
    @get:Column(name = "id")
    @get:Id
    var id = 0

    @get:Column(name = "name")
    @get:Basic
    var name: String? = null

    @get:OneToMany(mappedBy = "orderProductStatusByStatusId")
    var orderProductsById: Collection<OrderProductsEntity>? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as OrderProductStatusEntity
        if (id != that.id) return false
        return !if (name != null) name != that.name else that.name != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (name != null) name.hashCode() else 0
        return result
    }
}