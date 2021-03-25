package com.example.lab1.exception

class WrongRequestException(message: String, cause: Throwable? = null)
    : RuntimeException(message, cause)