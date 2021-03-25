package com.example.lab1.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handle(e : ResourceNotFoundException) : ResponseEntity<ErrorItem> {
        val error = ErrorItem(e.message!!)
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(WrongRequestException::class)
    fun handle(e : WrongRequestException) : ResponseEntity<ErrorItem> {
        val error = ErrorItem(e.message!!)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    data class ErrorItem(
            var message: String
    )

}