package com.example.lab1.controller

import com.example.lab1.dto.PickupPointDTO
import com.example.lab1.entities.CityEntity
import com.example.lab1.entities.PickupPointEntity
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
        fun getEntity() : PickupPointEntity{
            val city = CityEntity().apply { this.id = cityId }

            return PickupPointEntity().apply {
                address = pointAddress
                cost = deliveryCost
                cityByCityId = city
            }
        }
    }



}