package com.example.lab1.entities

import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserProvider(
        private var userRepository: UserRepository
) {

    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username) ?:
            throw ResourceNotFoundException("User not found!")
    }
}