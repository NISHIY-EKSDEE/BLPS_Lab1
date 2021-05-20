package com.example.lab1.repo

import com.example.lab1.entities.SellersProduct
import org.springframework.data.jpa.repository.JpaRepository

interface SellerProductRepo : JpaRepository<SellersProduct, Int> {
    fun save(sellerProductsEntity: SellersProduct) : SellersProduct
}