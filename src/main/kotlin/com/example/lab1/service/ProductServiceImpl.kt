package com.example.lab1.service

import com.example.lab1.entities.ProductEntity
import com.example.lab1.repo.ProductRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl : ProductService{

    @Autowired
    lateinit var productRepo : ProductRepo

    override fun create(product: ProductEntity): ProductEntity {
        return productRepo.save(product)
    }

}