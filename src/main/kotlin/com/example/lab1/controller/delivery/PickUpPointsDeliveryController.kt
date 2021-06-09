package com.example.lab1.controller.delivery

import com.example.lab1.dto.PickupPointAssembler
import com.example.lab1.dto.PickupPointRequest
import com.example.lab1.dto.PickupPointResponse
import com.example.lab1.entities.PickupPoint
import com.example.lab1.service.PickupPointService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/delivery/pickup/points")
class PickUpPointsDeliveryController(
        private var pickupPointService: PickupPointService
) {
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePoint(@RequestBody request: PickupPointRequest): PickupPointResponse {
        val resultPoint: PickupPoint = pickupPointService.changePoint(request)

        return PickupPointAssembler.buildDto(resultPoint)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPoint(@RequestBody request: PickupPointRequest): PickupPointResponse {
        val resultPoint: PickupPoint = pickupPointService.createPoint(request)

        return PickupPointAssembler.buildDto(resultPoint)
    }

    @DeleteMapping("/{pointId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePoint(@PathVariable pointId: Int) {
        pickupPointService.deletePoint(pointId)
    }
}