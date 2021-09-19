package com.example.lab1.controller.all

import com.example.lab1.dto.PickupPointAssembler
import com.example.lab1.dto.PickupPointResponse
import com.example.lab1.service.PickupPointService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/all/pickup/points")
class PickUpPointsAllController(
    private var pickupPointService: PickupPointService
) {
    /**
     * ALL могут посмотреть список всех точек откуда можно забрать товар.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list(): List<PickupPointResponse> {
        val all = pickupPointService.getAllAvailablePoints()

        return all.map(PickupPointAssembler::buildDto)
    }

    /**
     * ALL могут посмотреть подробную информацию о точке, откуда можно забрать товар
     */
    @GetMapping("/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPoint(@PathVariable pointId: Int): PickupPointResponse {
        val resultPoint = pickupPointService.getPoint(pointId)

        return PickupPointAssembler.buildDto(resultPoint)
    }
}