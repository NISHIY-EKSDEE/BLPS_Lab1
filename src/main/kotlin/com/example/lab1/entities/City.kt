package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "cities")
data class City (
        @Column(name = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column(name = "name")
        var name: String = "",

        @OneToMany(mappedBy = "city")
        var allPoints: Collection<PickupPoint> = emptyList()
)