package com.example.lab1.service

import com.example.lab1.dto.PickupPointDTO
import com.example.lab1.entities.PickupPointsEntity
import org.springframework.validation.annotation.Validated

@Validated
interface PickupPointService {
    fun getAllPoint() : List<PickupPointDTO>
    fun savePoint(point: PickupPointsEntity) : PickupPointDTO
    fun getPointById(id: Int): PickupPointDTO
}