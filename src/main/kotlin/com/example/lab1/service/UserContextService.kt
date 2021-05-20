package com.example.lab1.service

import com.example.lab1.entities.User
import com.example.lab1.entities.UserProvider
import com.example.lab1.exception.ResourceNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserContextService(
        private var userProvider: UserProvider
) {
    fun getUser(): User {
        val userDetails: UserDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user : User = userProvider.getUserByUsername(userDetails.username)

        return user
    }
}