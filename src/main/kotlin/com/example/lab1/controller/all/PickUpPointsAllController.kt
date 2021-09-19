package com.example.lab1.controller.all

import com.example.lab1.dto.PickupPointAssembler
import com.example.lab1.dto.PickupPointRequest
import com.example.lab1.dto.PickupPointResponse
import com.example.lab1.entities.PickupPoint
import com.example.lab1.service.PickupPointService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/all/pickup/points")
class PickUpPointsAllController(
    private var pickupPointService: PickupPointService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun listAllPickupPoints(): List<PickupPointResponse> {
        val all = pickupPointService.getAllAvailablePoints();

        return all.map(PickupPointAssembler::buildDto)
    }

    @GetMapping("/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPointById(@PathVariable pointId: Int): PickupPointResponse {
        val resultPoint = pickupPointService.getPoint(pointId)

        return PickupPointAssembler.buildDto(resultPoint)
    }
}