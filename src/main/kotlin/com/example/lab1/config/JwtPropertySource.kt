package com.example.lab1.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:jwt.properties")
class JwtPropertySource {
}