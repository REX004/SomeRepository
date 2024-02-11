package com.example.candystore.data.repository

import com.example.candystore.data.UserPreferences
import com.example.candystore.data.api.AuthApi
import com.example.candystore.data.models.UserAuth

class AuthRepository(
    private val authApi: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun login(userAuth: UserAuth) = safeApiCall {
        authApi.login(userAuth)
    }



    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

}