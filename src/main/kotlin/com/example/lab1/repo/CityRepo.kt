package com.example.lab1.repo

import com.example.lab1.entities.CitiesEntity
import org.springframework.data.repository.CrudRepository

interface CityRepo : CrudRepository<CitiesEntity, Int> {
}