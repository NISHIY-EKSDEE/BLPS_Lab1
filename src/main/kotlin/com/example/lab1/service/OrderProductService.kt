package com.example.lab1.service

import com.example.lab1.entities.OrderProductsEntity

interface OrderProductService {
    fun save(ent: OrderProductsEntity): OrderProductsEntity
}