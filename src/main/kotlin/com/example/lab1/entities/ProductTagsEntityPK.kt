package com.example.lab1.entities

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Id

class ProductTagsEntityPK : Serializable {
    @get:Id
    @get:Column(name = "product_id")
    var productId = 0

    @get:Id
    @get:Column(name = "tag_id")
    var tagId = 0
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ProductTagsEntityPK
        if (productId != that.productId) return false
        return tagId == that.tagId
    }

    override fun hashCode(): Int {
        var result = productId
        result = 31 * result + tagId
        return result
    }
}