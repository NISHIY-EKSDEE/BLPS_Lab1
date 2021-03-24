package com.example.lab1.service

import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.SellerProductsEntity
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min;

@Validated
interface ProductSellerService {
    fun getAllProducts() : List<SellerProductDTO>
    fun getProduct(@Min(value = 1L, message = "Invalid product ID.") id: Long) : SellerProductDTO
    fun create(product: SellerProductsEntity) : SellerProductDTO
}
