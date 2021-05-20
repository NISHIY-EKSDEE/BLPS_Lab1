package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "payment_methods")
data class PaymentMethod(
    @Column(name = "id")
    @Id
    var id: Int = 0,

    @Column(name = "name")
    var name: String = ""
)
