package com.example.lab1.repo

import com.example.lab1.entities.CityEntity
import org.springframework.data.repository.CrudRepository

interface CityRepo : CrudRepository<CityEntity, Int> {
}