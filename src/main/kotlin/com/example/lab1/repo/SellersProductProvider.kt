package com.example.lab1.repo

import com.example.lab1.entities.SellersProduct
import com.example.lab1.exception.ResourceNotFoundException
import org.springframework.stereotype.Repository

@Repository
class SellersProductProvider(
        private var sellerProductRepo: SellerProductRepo
) {

    fun getAll(): MutableList<SellersProduct> {
        return sellerProductRepo.findAll()
    }

    fun get(productId: Int): SellersProduct {
        return sellerProductRepo.findById(productId).orElseThrow {
            throw ResourceNotFoundException("Product doesn't exist!")
        }
    }

    fun delete(productId: Int) {
        if (!sellerProductRepo.existsById(productId))
            throw ResourceNotFoundException("Product doesn't exist!")

        sellerProductRepo.deleteById(productId)
    }

    fun save(sproduct: SellersProduct): SellersProduct {
        return sellerProductRepo.save(sproduct)
    }
}