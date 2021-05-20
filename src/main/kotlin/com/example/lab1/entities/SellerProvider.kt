package com.example.lab1.entities

import com.example.lab1.exception.WrongRequestException
import com.example.lab1.repo.SellerRepo
import org.springframework.stereotype.Repository

@Repository
class SellerProvider(
        private var sellerRepository: SellerRepo
) {
    fun getByUser(user: User): Seller {
        val seller = sellerRepository.findByUserId(user.id)
        val sellers = sellerRepository.findAll()

        return sellerRepository.findByUser(user) ?:
            throw WrongRequestException("This user isn't seller!")
    }

}