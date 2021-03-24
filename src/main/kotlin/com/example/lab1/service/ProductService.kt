package com.example.lab1.service

import com.example.lab1.entities.ProductsEntity
import com.example.lab1.repo.ProductRepo
import org.springframework.beans.factory.annotation.Autowired

interface ProductService {
    fun create(product : ProductsEntity) : ProductsEntity
}