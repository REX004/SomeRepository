package com.example.candystore.data.api

import com.example.candystore.data.models.AuthResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("auth/token")
    suspend fun validate(
        @Header("Authorization") authHeader: String
    ) : AuthResponse

    @DELETE("logout")
    suspend fun logout(
        @Header("Authorization") authHeader: String
    ): Unit
}