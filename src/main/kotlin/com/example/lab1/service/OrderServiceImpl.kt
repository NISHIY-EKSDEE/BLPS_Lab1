package com.example.lab1.service

import com.example.lab1.dto.OrderAssembler
import com.example.lab1.dto.OrderDTO
import com.example.lab1.entities.OrdersEntity
import com.example.lab1.entities.UsersEntity
import com.example.lab1.repo.OrderRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl : OrderService {

    @Autowired
    lateinit var orderRepo: OrderRepo

    override fun findOrdersByUserId(id: Long): List<OrderDTO> {
        val user = UsersEntity()
        user.id = id.toInt()
        return orderRepo.findAllByUsersByUserId(user).map(OrderAssembler::buildDto)
    }

    override fun findOrderById(id: Int): OrderDTO {
        return OrderAssembler.buildDto(orderRepo.findById(id).orElseThrow{
            ResourceNotFoundException("Order not found!")
        })
    }

    override fun saveOrderEntity(order: OrdersEntity): OrderDTO {
        return OrderAssembler.buildDto(orderRepo.save(order))
    }
}