package com.example.lab1.controller

import com.example.lab1.dto.PickupPointAssembler
import com.example.lab1.dto.PickupPointRequest
import com.example.lab1.dto.PickupPointResponse
import com.example.lab1.entities.PickupPoint
import com.example.lab1.service.PickupPointService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pickup/points")
class PickUpPointsController(
    private var pickupPointService: PickupPointService
) {
    /**
     * ALL могут посмотреть список всех точек откуда можно забрать товар.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list(): List<PickupPointResponse> {
        val all = pickupPointService.getAllAvailablePoints();

        return all.map(PickupPointAssembler::buildDto)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePoint(@RequestBody request: PickupPointRequest): PickupPointResponse {
        val resultPoint: PickupPoint = pickupPointService.changePoint(request)

        return PickupPointAssembler.buildDto(resultPoint)
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

    /**
     * DELIVERY может добавить новую точку на карту.
     */
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