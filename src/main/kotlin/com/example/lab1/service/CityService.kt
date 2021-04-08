package com.example.lab1.service

import com.example.lab1.entities.CityEntity
import org.springframework.validation.annotation.Validated

@Validated
interface CityService {
    fun findAll() : Iterable<CityEntity>
    fun save(city : CityEntity) : CityEntity
}