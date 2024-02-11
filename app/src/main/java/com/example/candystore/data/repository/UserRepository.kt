package com.example.candystore.data.repository

import com.example.candystore.data.api.UserApi

class UserRepository(
    private val userApi: UserApi
) : BaseRepository() {

    suspend fun logout(token: String) = safeApiCall {
        userApi.logout(token)
    }
}