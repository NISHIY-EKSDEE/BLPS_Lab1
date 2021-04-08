package com.example.lab1.repo

import com.example.lab1.entities.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepo : CrudRepository<ProductEntity, Int>{

}