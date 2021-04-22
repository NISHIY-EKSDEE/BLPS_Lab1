package com.example.lab1.service

import com.example.lab1.entities.User
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepo : UserRepository

    @Autowired
    lateinit var passwordEncoder : PasswordEncoder

    fun existsByLogin(login: String) : Boolean {
        return userRepo.existsByUsername(login)
    }

    fun saveUser(user: User) : User {
        user.password = passwordEncoder.encode(user.password)
        return userRepo.save(user)
    }

    fun getUserByLoginAndPassword(login: String, password: String): User? {
        val user : User = userRepo.findByUsername(login) ?:
                throw ResourceNotFoundException("User isn't found")

        if (passwordEncoder.matches(password, user.password)) {
            return user
        }
        else throw WrongRequestException("Password is wrong!")
    }

    fun getUserByUsername(username: String) : User? {
        return userRepo.findByUsername(username)
    }
}