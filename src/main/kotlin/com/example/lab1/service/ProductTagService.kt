package com.example.lab1.service

import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.ProductEntity
import com.example.lab1.entities.TagEntity

interface ProductTagService {
    fun getProductsByTags(tags: Collection<TagEntity>) : Collection<ProductEntity>
    fun getSellerProductsDTOByProducts(products: Collection<ProductEntity>) : Collection<SellerProductDTO>
}