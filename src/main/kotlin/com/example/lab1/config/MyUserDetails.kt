package com.example.lab1.config

import com.example.lab1.entities.User
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.repo.UserRepository
import lombok.RequiredArgsConstructor

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class MyUserDetails(
        private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username) ?: throw WrongRequestException("User not found!")



        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.password)
                .authorities(user.role.authority)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}