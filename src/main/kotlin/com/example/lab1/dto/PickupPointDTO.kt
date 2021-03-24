package com.example.lab1.dto

import com.example.lab1.entities.PickupPointsEntity
import com.example.lab1.entities.SellerProductsEntity

data class PickupPointDTO(
        val cityId : Int,
        val city : String,
        val address : String,
        val cost : Int
)

object PickupPointAssembler{
    fun buildDto(ent: PickupPointsEntity) : PickupPointDTO {
        return PickupPointDTO(
                ent.citiesByCityId!!.id,
                ent.citiesByCityId?.name!!,
                ent.address!!,
                ent.cost
        )
    }
}


