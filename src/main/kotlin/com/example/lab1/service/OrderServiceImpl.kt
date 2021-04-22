package com.example.lab1.service

import com.example.lab1.dto.OrderAssembler
import com.example.lab1.dto.OrderDTO
import com.example.lab1.entities.OrderEntity
import com.example.lab1.entities.User
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.OrderRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl : OrderService {

    @Autowired
    lateinit var orderRepo: OrderRepo

    override fun findOrdersByUserId(id: Long): List<OrderDTO> {
        val user = User()
        user.id = id.toInt().toLong()
        return orderRepo.findAllByUserByUserId(user).map(OrderAssembler::buildDto)
    }

    override fun findOrderById(id: Int): OrderDTO {
        return OrderAssembler.buildDto(orderRepo.findById(id).orElseThrow{
            ResourceNotFoundException("Order not found!")
        })
    }

    override fun findOrderByIdRetEnt(id: Int): OrderEntity {
        return orderRepo.findById(id).orElseThrow{
            ResourceNotFoundException("Order not found!")
        }
    }

    override fun saveOrderEntity(order: OrderEntity): OrderDTO {
        return OrderAssembler.buildDto(orderRepo.save(order))
    }

    override fun saveOrderEntityAndRetEntity(order: OrderEntity): OrderEntity {
        return orderRepo.save(order)
    }

    override fun deleteById(id: Int) {
        orderRepo.deleteById(id)
    }

}