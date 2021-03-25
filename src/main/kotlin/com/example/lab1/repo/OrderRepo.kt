package com.example.lab1.repo

import com.example.lab1.entities.OrdersEntity
import com.example.lab1.entities.UsersEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepo: CrudRepository<OrdersEntity, Int> {
    fun findAllByUsersByUserId(usersByUserId: UsersEntity) : List<OrdersEntity>
}