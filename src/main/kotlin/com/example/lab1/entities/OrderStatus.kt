package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "order_status")
data class OrderStatus(
    @Column(name = "id")
    @Id
    var id : Int = 0,

    @Column
    var name: String = "",

    @OneToMany(mappedBy = "status")
    var orders: Collection<Order> = emptyList()
)