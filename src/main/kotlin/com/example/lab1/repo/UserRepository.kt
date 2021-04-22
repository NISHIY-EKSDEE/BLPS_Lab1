package com.example.lab1.repo

import com.example.lab1.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>  {
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
    fun findByUsernameAndPassword(login: String, password: String): User?
}