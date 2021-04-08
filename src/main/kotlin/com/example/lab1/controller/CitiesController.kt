package com.example.lab1.controller

import com.example.lab1.entities.CityEntity
import com.example.lab1.service.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("/api/cities")
class CitiesController {

    @Autowired
    lateinit var citiesService : CityService

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list() : Iterable<CityEntity> {
        return citiesService.findAll()
    }

    @PostMapping
    fun create(@RequestBody city : CityForm) : ResponseEntity<CityEntity> {
        return ResponseEntity(
                citiesService.save(city.getEntity()),
                HttpStatus.CREATED
        )
    }

    data class CityForm(
            val cityName : String
    ) {
        fun getEntity() : CityEntity {
            return CityEntity().apply {
                name = cityName
            }
        }
    }

}