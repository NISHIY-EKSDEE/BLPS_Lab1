package com.example.lab1.service

import com.example.lab1.entities.ProductEntity

interface ProductService {
    fun create(product : ProductEntity) : ProductEntity
}