package com.example.lab1.dto

import com.example.lab1.entities.PickupPoint

data class PickupPointResponse(
    val pointId: Int,
    val cityId : Int,
    val city : String,
    val address : String,
    val cost : Int
)

data class PickupPointRequest(
    val cityId: Int = 0,
    val pointId: Int = 0,
    val address: String = "",
    val cost: Int = 0
){
    fun buildForPost() : PickupPoint {
        val point: PickupPoint = PickupPoint()

        point.city.id = cityId
        point.address = address
        point.cost = cost

        return point
    }

    fun buildForPut() : PickupPoint {
        val point: PickupPoint = PickupPoint()

        point.city.id = cityId
        point.id = pointId
        point.address = address
        point.cost = cost

        return point
    }
}

object PickupPointAssembler{
    fun buildDto(ent: PickupPoint) : PickupPointResponse {
        return PickupPointResponse(
                ent.id,
                ent.city.id,
                ent.city.name,
                ent.address,
                ent.cost
        )
    }
}


