package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "order_product_status")
data class OrderProductStatus(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(name = "name")
        val name: String = ""
)