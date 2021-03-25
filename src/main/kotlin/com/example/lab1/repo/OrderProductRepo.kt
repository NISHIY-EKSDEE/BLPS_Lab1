package com.example.lab1.repo

import com.example.lab1.entities.OrderProductsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepo : CrudRepository<OrderProductsEntity, Int>{
}