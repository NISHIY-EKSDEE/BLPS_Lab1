package com.example.lab1.service

import com.example.lab1.entities.OrderProductsEntity
import com.example.lab1.repo.OrderProductRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderProductServiceImpl : OrderProductService {

    @Autowired
    lateinit var orderProductRepo: OrderProductRepo

    override fun save(ent: OrderProductsEntity): OrderProductsEntity {
            return orderProductRepo.save(ent)
    }
}