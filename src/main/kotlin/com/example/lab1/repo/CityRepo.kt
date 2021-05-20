package com.example.lab1.repo

import com.example.lab1.entities.City
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepo : JpaRepository<City, Int> {
}
