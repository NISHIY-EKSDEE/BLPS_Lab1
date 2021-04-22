package com.example.lab1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.jta.JtaTransactionManager

@SpringBootApplication
class Lab1Application

fun main(args: Array<String>) {
    runApplication<Lab1Application>(*args)
}
