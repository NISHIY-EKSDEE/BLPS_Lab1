package com.example.lab1.repo

import com.example.lab1.entities.PickupPoint
import org.springframework.data.jpa.repository.JpaRepository

interface PickupPointRepo : JpaRepository<PickupPoint, Int> {
}