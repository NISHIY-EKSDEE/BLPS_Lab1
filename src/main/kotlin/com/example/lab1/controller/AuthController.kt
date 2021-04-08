package com.example.lab1.controller

import com.example.lab1.config.jwt.JwtProvider
import com.example.lab1.dto.AuthResponse
import com.example.lab1.entities.UserEntity
import com.example.lab1.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AuthController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @PostMapping("/login")
    fun auth(@RequestBody request : AuthRequest) : AuthResponse {
        val user: UserEntity? = userService.getUserByLoginAndPassword(request.login, request.password)
        if (Objects.isNull(user)) {
            throw ResourceNotFoundException("user isn't found")
        }

        val token : String = jwtProvider.generateToken(request.login)
        return AuthResponse(token)
    }

    @GetMapping("/testToken")
    fun test() : String {
        return jwtProvider.DAYS_TILL_EXPIRE.toString()
    }

    data class AuthRequest(
            val login : String,
            val password : String
    )

}