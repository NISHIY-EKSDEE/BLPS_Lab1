package com.example.lab1.service

import com.example.lab1.entities.OrderStatusEntity
import com.example.lab1.repo.OrderStatusRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderStatusServiceImpl : OrderStatusService {

    @Autowired
    lateinit var orderStatusRepo : OrderStatusRepo

    private var isListSet : Boolean = false

    var statuses : List<OrderStatusEntity> = emptyList()
        get() {
            return if (isListSet) field
            else {
                field = orderStatusRepo.findAll().toList()
                isListSet = true
                field
            }
        }

    override fun findAllStatuses(): List<OrderStatusEntity> {
        return statuses
    }

}