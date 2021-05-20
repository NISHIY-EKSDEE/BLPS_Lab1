package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "seller_products")
data class SellersProduct(
        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column(name = "cost")
        var cost: Int = 0,

        @Column(name = "total_amount")
        var totalAmount: Int = 0,

        @JoinColumn(name = "product_id", referencedColumnName = "id")
        @ManyToOne(cascade = [CascadeType.ALL])
        var product: Product = Product(),

        @JoinColumn(name = "seller_id", referencedColumnName = "id")
        @ManyToOne
        var seller: Seller = Seller()
)