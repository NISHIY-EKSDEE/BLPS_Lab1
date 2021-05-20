package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "pickup_points")
data class PickupPoint (
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column(name = "address")
        var address: String = "",

        @Column(name = "cost")
        var cost: Int = 0,

        @JoinColumn(name = "city_id", referencedColumnName = "id")
        @ManyToOne
        var city: City = City()
)