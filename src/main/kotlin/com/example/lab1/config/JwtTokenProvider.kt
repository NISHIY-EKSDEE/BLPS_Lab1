package com.example.lab1.config

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
@RequiredArgsConstructor
class JwtTokenProvider(
        @Value("\${security.jwt.token.secret-key:secret-key}")
        private var secretKey: String,

        @Value("\${security.jwt.token.expire-length:3600000}")
        private val validityInMilliseconds: Long = 3600000, //1 h

        private val myUserDetails: MyUserDetails
) {

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String?): String {
        return Jwts.claims().setSubject(username).let {
            val now = Date()
            Jwts.builder().setClaims(it).setIssuedAt(now).setExpiration(Date(now.time + validityInMilliseconds))
                    .signWith(SignatureAlgorithm.HS512, secretKey).compact()
        }



//        val claims = Jwts.claims().setSubject(username)
//        claims["auth"] = roles.stream().map{
//            s: Role -> SimpleGrantedAuthority(s.authority)
//        }.filter(Objects::nonNull).collect(Collectors.toList())
//
//        val now = Date()
//
//        val validity = Date(now.time + validityInMilliseconds)
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails = myUserDetails.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            throw Exception("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw Exception("Expired or invalid JWT token")
        }
    }
}