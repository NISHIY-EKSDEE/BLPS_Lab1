package com.example.lab1.service

import com.example.lab1.dto.PickupPointRequest
import com.example.lab1.entities.PickupPoint
import com.example.lab1.repo.PickupPointProvider
import org.springframework.stereotype.Service

@Service
class PickupPointService(
        private var pickupPointProvider: PickupPointProvider
) {
    fun getAllAvailablePoints(): List<PickupPoint> {
        return pickupPointProvider.getAll();
    }

    fun changePoint(request: PickupPointRequest): PickupPoint {
        val point = request.buildForPut()

        return pickupPointProvider.modify(point)
    }

    fun createPoint(request: PickupPointRequest): PickupPoint {
        val point = request.buildForPost()

        return pickupPointProvider.createOrModify(point)
    }

    fun deletePoint(pointId: Int) {
        pickupPointProvider.delete(pointId)
    }

    fun getPoint(pointId: Int): PickupPoint {
        return pickupPointProvider.get(pointId)
    }

}