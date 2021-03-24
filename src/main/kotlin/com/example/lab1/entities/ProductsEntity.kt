package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "products", schema = "s265098", catalog = "studs")
class ProductsEntity {
    @get:Column(name = "id")
    @get:Id
    var id = 0

    @get:Column(name = "name")
    @get:Basic
    var name: String? = null

    @get:Column(name = "description")
    @get:Basic
    var description: String? = null

    @get:OneToMany(mappedBy = "productsByProductId")
    var productTagsById: Collection<ProductTagsEntity>? = null

    @get:OneToMany(mappedBy = "productsByProductId")
    var sellerProductsById: Collection<SellerProductsEntity>? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ProductsEntity
        if (id != that.id) return false
        if (if (name != null) name != that.name else that.name != null) return false
        return !if (description != null) description != that.description else that.description != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (name != null) name.hashCode() else 0
        result = 31 * result + if (description != null) description.hashCode() else 0
        return result
    }
}