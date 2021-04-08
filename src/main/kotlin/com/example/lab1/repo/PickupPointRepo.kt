package com.example.lab1.repo

import com.example.lab1.entities.PickupPointEntity
import org.springframework.data.repository.CrudRepository

interface PickupPointRepo : CrudRepository<PickupPointEntity, Int> {
}