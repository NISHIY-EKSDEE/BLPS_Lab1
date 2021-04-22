package com.example.lab1.controller

import com.example.lab1.config.JwtTokenProvider
import com.example.lab1.dto.AuthResponse
import com.example.lab1.entities.User
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtProvider: JwtTokenProvider

    @PostMapping("/login")
    fun auth(@RequestBody request : AuthRequest) : AuthResponse {
        val user : User = userService.getUserByLoginAndPassword(request.login, request.password) ?:
                throw ResourceNotFoundException("user isn't found")

        val token : String = jwtProvider.createToken(request.login)
        return AuthResponse(token)
    }

    data class AuthRequest(
            val login : String,
            val password : String
    )

}