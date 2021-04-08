package com.example.lab1.config.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import io.jsonwebtoken.*
import java.util.*

@Component
class JwtProvider {
    @Value("\${jwt.days}")
    var DAYS_TILL_EXPIRE : Long = 1

    @Value("\${jwt.secret}")
    lateinit var jwtSecret: String



    fun generateToken(login: String): String {
        val date = Date.from(
                LocalDate.now().plusDays(DAYS_TILL_EXPIRE).atStartOfDay(ZoneId.systemDefault()).toInstant()
        )

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }
}