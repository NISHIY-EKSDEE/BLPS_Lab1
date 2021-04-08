package com.example.lab1.dto

import com.example.lab1.entities.SellerProductsEntity

data class SellerProductDTO(
        val sellerProductId: Int,
        val sellerId: Int?,
        val productName: String,
        val productDescription: String,
        val sellerName: String?,
        val cost: Int,
        val totalAmount: Int
)

data class SellerProductShortDTO(
        val sellerProductId: Int,
        val sellerId: Int?,
        val productName: String?,
        val sellerName: String?,
        val cost: Int?
)



object SellerProductAssembler{
    fun buildDto(ent: SellerProductsEntity) : SellerProductDTO {
        return SellerProductDTO(
                ent.id,
                ent.sellerBySellerId?.id,
                ent.productByProductId?.name!!,
                ent.productByProductId?.description!!,
                ent.sellerBySellerId?.name,
                ent.cost!!,
                ent.totalAmount
        )
    }

    fun buildShortDto(ent: SellerProductsEntity) : SellerProductShortDTO {
        return SellerProductShortDTO(
                ent.id,
                ent.sellerBySellerId?.id,
                ent.productByProductId?.name,
                ent.sellerBySellerId?.name,
                ent.cost
        )

    }

}
