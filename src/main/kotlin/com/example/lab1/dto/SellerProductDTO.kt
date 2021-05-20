package com.example.lab1.dto

import com.example.lab1.entities.Product
import com.example.lab1.entities.SellersProduct

data class SProductResponse(
        val sProductId: Int,
        val sellerId: Int,
        val sellerName: String,
        val productName: String,
        val productDescription: String,
        val cost: Int,
        val totalAmount: Int
)


object SellerProductResponseAssembler {
    fun buildDto(ent: SellersProduct): SProductResponse {
        return SProductResponse(
                ent.id,
                ent.seller.id,
                ent.seller.name,
                ent.product.name,
                ent.product.description,
                ent.cost,
                ent.totalAmount
        )
    }
}

data class SProductShort(
        val sellerProductId: Int,
        val sellerId: Int,
        val productName: String,
        val sellerName: String,
        val cost: Int
)

object SProductShortAssembler {
    fun buildDto(ent: SellersProduct): SProductShort {
        return SProductShort(
                ent.id,
                ent.seller.id,
                ent.product.name,
                ent.seller.name,
                ent.cost
        )
    }
}

data class ProductRequest(
        val cost : Int,
        val totalAmount : Int,
        val prodName : String,
        val prodDescription : String
) {
    fun getProductEnt() : Product = Product().apply {
        this.description = prodDescription
        this.name = prodName
    }
}