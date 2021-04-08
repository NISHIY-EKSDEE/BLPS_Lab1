package com.example.lab1.repo

import com.example.lab1.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<UserEntity, Int> {
    fun findByLoginAndPassword(login: String, password: String) : UserEntity?
    fun existsByLogin(login: String) : Boolean
}
