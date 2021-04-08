package com.example.lab1.service

import com.example.lab1.entities.UserEntity
import com.example.lab1.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepo : UserRepo

    @Autowired
    lateinit var passwordEncoder : PasswordEncoder

    fun getUserByLoginAndPassword(login: String, password: String) : UserEntity? {
        return userRepo.findByLoginAndPassword(login, password)
    }

    fun existsByLogin(login: String) : Boolean {
        return userRepo.existsByLogin(login)
    }

    fun saveUser(user: UserEntity) : UserEntity {
        user.password = passwordEncoder.encode(user.password)
        return userRepo.save(user)
    }

}