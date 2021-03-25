package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "product_tags", schema = "s265098", catalog = "studs")
class ProductTagsEntity {

    @get:Id
    var id: Long? = null

    @get:JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var productsByProductId: ProductsEntity? = null

    @get:JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var tagsByTagId: TagsEntity? = null
}