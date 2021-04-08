package com.example.lab1.exception

class UserAlreadyExistsException(message: String, cause: Throwable? = null)
    : RuntimeException(message, cause)