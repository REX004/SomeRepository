package com.example.candystore.data.models

import com.example.candystore.utils.Roles

data class User(
    val firstName: String,
    val secondName: String,
    val role: Roles,
    val email: String,
    val phoneNumber: String,
    val manager: Manager
)
