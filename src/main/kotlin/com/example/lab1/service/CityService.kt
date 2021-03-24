package com.example.lab1.service

import com.example.lab1.entities.CitiesEntity
import org.springframework.validation.annotation.Validated

@Validated
interface CityService {
    fun findAll() : Iterable<CitiesEntity>
    fun save(city : CitiesEntity) : CitiesEntity
}