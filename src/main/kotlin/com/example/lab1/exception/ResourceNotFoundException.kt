package com.example.lab1.exception


import kotlin.RuntimeException

class ResourceNotFoundException(message: String, cause: Throwable? = null)
    : RuntimeException(message, cause)