package com.example.lab1.service

import com.example.lab1.entities.SellerEntity
import com.example.lab1.entities.User
import com.example.lab1.repo.SellerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SellerService {
    @Autowired
    lateinit var sellerRepo: SellerRepo

    fun getSellerByUser(user: User): SellerEntity? {
        return sellerRepo.findByUser(user)
    }


}