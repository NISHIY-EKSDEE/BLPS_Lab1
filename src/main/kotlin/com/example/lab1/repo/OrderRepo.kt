package com.example.lab1.repo

import com.example.lab1.entities.OrderEntity
import com.example.lab1.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepo: CrudRepository<OrderEntity, Int> {
    fun findAllByUserByUserId(userByUserId: User) : List<OrderEntity>
}