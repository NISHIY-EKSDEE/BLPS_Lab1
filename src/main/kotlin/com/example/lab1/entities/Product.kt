package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "products")
data class Product (
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Int = 0,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "description")
    var description: String = "",

    @OneToMany(mappedBy = "product")
    var tags: Collection<ProductTagEntity> = emptyList()
)
