package com.example.lab1.repo

import com.example.lab1.entities.OrderProducts
import org.springframework.data.jpa.repository.JpaRepository

interface OrderProductRepo : JpaRepository<OrderProducts, Int>{
}