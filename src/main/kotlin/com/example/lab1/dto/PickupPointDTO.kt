package com.example.lab1.dto

import com.example.lab1.entities.PickupPointEntity

data class PickupPointDTO(
        val cityId : Int,
//        val city : String,
        val address : String,
        val cost : Int
)

object PickupPointAssembler{
    fun buildDto(ent: PickupPointEntity) : PickupPointDTO {
        return PickupPointDTO(
                ent.cityByCityId!!.id,
//                ent.citiesByCityId?.name!!,
                ent.address!!,
                ent.cost
        )
    }
}


