package com.example.lab1.exception

class UserAlreadyAuthenticatedException(message: String, cause: Throwable? = null)
    : RuntimeException(message, cause)