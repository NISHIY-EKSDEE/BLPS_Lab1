package com.example.lab1.controller

import com.example.lab1.entities.Role
import com.example.lab1.entities.User
import com.example.lab1.exception.UserAlreadyExistsException
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class RegistrationController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var registrationRequestValidator: RegistrationRequestValidator

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    fun registerUser(
            @RequestBody registrationRequest: RegistrationRequest
    ) {
        registrationRequestValidator.validate(registrationRequest)

        if (userService.existsByLogin(registrationRequest.login)) {
            throw UserAlreadyExistsException("user with this login already exists");
        }

        val user = User()
        user.username = registrationRequest.login
        user.password = registrationRequest.password
        user.role = Role.ROLE_CLIENT

        userService.saveUser(user)
    }

    data class RegistrationRequest(
            val login: String,
            val password: String,
            val repeatPassword: String
    )

    class RegistrationRequestValidator {
        companion object {
            const val LOGIN_MIN_LENGTH = 6;
            const val LOGIN_MAX_LENGTH = 30;
            const val PASSWORD_MIN_LENGTH = 6;
            const val PASSWORD_MAX_LENGTH = 30;
        }

        fun validate(registrationRequest: RegistrationRequest) {
            if (registrationRequest.login.length !in LOGIN_MIN_LENGTH..LOGIN_MAX_LENGTH)
                throw WrongRequestException("wrong login length")

            if (registrationRequest.password.length !in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH)
                throw WrongRequestException("wrong password length")

            if (registrationRequest.password != registrationRequest.repeatPassword) {
                throw WrongRequestException("password and repeatPassword aren't equal")
            }
        }
    }

    @Bean
    fun getValidator(): RegistrationRequestValidator {
        return RegistrationRequestValidator()
    }

}