package com.example.candystore.data.models

data class AuthResponse(
    val token: String,
    val tokenType: String = "Bearer",
    val user: User,
)
