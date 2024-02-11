package com.example.candystore.data.api

import com.example.candystore.data.models.AuthResponse
import com.example.candystore.data.models.UserAuth
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body body: UserAuth
    ): AuthResponse


}