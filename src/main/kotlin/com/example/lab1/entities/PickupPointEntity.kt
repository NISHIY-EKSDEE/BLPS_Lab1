package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "pickup_points", schema = "s265098", catalog = "studs")
class PickupPointEntity {
    @get:Column(name = "id")
    @get:Id
    var id = 0

    @get:Column(name = "address")
    @get:Basic
    var address: String? = null

    @get:Column(name = "cost")
    @get:Basic
    var cost = 0

    @get:OneToMany(mappedBy = "pickupPointsByPickupPointId")
    var orderById: Collection<OrderEntity>? = null

    @get:JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    @get:ManyToOne
    var cityByCityId: CityEntity? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PickupPointEntity
        if (id != that.id) return false
        if (cost != that.cost) return false
        return !if (address != null) address != that.address else that.address != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (address != null) address.hashCode() else 0
        result = 31 * result + cost
        return result
    }
}