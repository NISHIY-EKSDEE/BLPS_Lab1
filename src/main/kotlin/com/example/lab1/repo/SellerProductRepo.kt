package com.example.lab1.repo

import com.example.lab1.entities.SellerProductsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SellerProductRepo : CrudRepository<SellerProductsEntity, Long>{
    fun save(sellerProductsEntity: SellerProductsEntity) : SellerProductsEntity
}