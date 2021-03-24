package com.example.lab1.controller

import com.example.lab1.dto.PickupPointDTO
import com.example.lab1.entities.CitiesEntity
import com.example.lab1.entities.PickupPointsEntity
import com.example.lab1.service.PickupPointService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pickup/points")
class PickUpPointsController {

    @Autowired
    lateinit var pickupPointService: PickupPointService

    //TODO сделать фильтрацию
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list() : List<PickupPointDTO> = pickupPointService.getAllPoint()

    @GetMapping("/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOne(@PathVariable pointId: Int) = pickupPointService.getPointById(pointId)

    @PostMapping
    fun create(@RequestBody form : PointForm) : ResponseEntity<PickupPointDTO> {
        val point = form.getEntity()

        return ResponseEntity(pickupPointService.savePoint(point), HttpStatus.CREATED)
    }

    data class PointForm(
            val pointAddress: String,
            val deliveryCost: Int,
            val cityId: Int
    ) {
        fun getEntity() : PickupPointsEntity{
            val city = CitiesEntity().apply { this.id = cityId }

            return PickupPointsEntity().apply {
                address = pointAddress
                cost = deliveryCost
                citiesByCityId = city
            }
        }
    }



}