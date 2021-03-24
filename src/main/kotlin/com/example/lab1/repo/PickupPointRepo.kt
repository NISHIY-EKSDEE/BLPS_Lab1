package com.example.lab1.repo

import com.example.lab1.entities.PickupPointsEntity
import org.springframework.data.repository.CrudRepository

interface PickupPointRepo : CrudRepository<PickupPointsEntity, Int> {
}