package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "product_tags", schema = "s265098", catalog = "studs")
@IdClass(ProductTagsEntityPK::class)
class ProductTagsEntity {
    @get:Column(name = "product_id")
    @get:Id
    var productId = 0

    @get:Column(name = "tag_id")
    @get:Id
    var tagId = 0

    @get:JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var productsByProductId: ProductsEntity? = null

    @get:JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var tagsByTagId: TagsEntity? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ProductTagsEntity
        if (productId != that.productId) return false
        return tagId == that.tagId
    }

    override fun hashCode(): Int {
        var result = productId
        result = 31 * result + tagId
        return result
    }
}