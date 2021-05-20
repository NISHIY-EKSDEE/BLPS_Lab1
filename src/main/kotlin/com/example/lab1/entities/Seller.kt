package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "sellers")
data class Seller (
        @Column(name = "id")
        @Id
        var id : Int = 0,

        @Column(name = "name")
        var name: String = "",

        @Column(name = "description")
        var description: String = "",

        @Column(name = "warehouse_address")
        var warehouseAddress: String = "",

        @OneToMany(mappedBy = "seller")
        var sellerProductsById: Collection<SellersProduct> = emptyList(),

        @ManyToOne
        @PrimaryKeyJoinColumn
        var user: User = User()
)