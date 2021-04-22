package com.example.lab1.repo

import com.example.lab1.entities.SellerEntity
import com.example.lab1.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface SellerRepo : JpaRepository<SellerEntity, Int> {
    fun findByUser(user: User) : SellerEntity?
}