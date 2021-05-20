package com.example.lab1.repo

import com.example.lab1.entities.Seller
import com.example.lab1.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface SellerRepo : JpaRepository<Seller, Int> {
    fun findByUser(user: User): Seller?
    fun findByUserId(user_id: Int): Seller?
}