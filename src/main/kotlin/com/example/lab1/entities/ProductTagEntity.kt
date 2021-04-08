package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "product_tags", schema = "s265098", catalog = "studs")
class ProductTagEntity {

    @get:Id
    var id: Int? = null

    @get:JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var productByProductId: ProductEntity? = null

    @get:JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var tagByTagId: TagEntity? = null
}