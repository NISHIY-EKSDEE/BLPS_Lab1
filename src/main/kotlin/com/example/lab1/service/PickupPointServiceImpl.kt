package com.example.lab1.service

import com.example.lab1.dto.PickupPointAssembler
import com.example.lab1.dto.PickupPointDTO
import com.example.lab1.entities.PickupPointsEntity
import com.example.lab1.repo.PickupPointRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class PickupPointServiceImpl : PickupPointService {

    @Autowired
    lateinit var pickupPointRepo : PickupPointRepo

    override fun getAllPoint(): List<PickupPointDTO> {
        return pickupPointRepo.findAll().map(PickupPointAssembler::buildDto)
    }

    override fun getPointById(id : Int): PickupPointDTO {
        return PickupPointAssembler.buildDto(
                pickupPointRepo.findById(id).orElseThrow {
                    ResourceNotFoundException("Pickup point not found!")
                }
        )
    }

    override fun savePoint(point: PickupPointsEntity): PickupPointDTO{
        return PickupPointAssembler.buildDto(point)
    }
}