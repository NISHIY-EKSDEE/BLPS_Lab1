package com.example.lab1.service

import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.ProductsEntity
import com.example.lab1.entities.TagsEntity

interface ProductTagService {
    fun getProductsByTags(tags: Collection<TagsEntity>) : Collection<ProductsEntity>
    fun getSellerProductsDTOByProducts(products: Collection<ProductsEntity>) : Collection<SellerProductDTO>
}