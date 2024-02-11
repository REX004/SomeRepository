package com.example.candystore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.models.AuthResponse
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val authResponse: LiveData<Resource<AuthResponse>>
        get() = _authResponse

    fun login(userAuth: UserAuth) = viewModelScope.launch {
        _authResponse.value = Resource.Loading
        _authResponse.postValue(authRepository.login(userAuth))

    }

    suspend fun saveAuthToken(token: String) {
        authRepository.saveAuthToken(token)
    }

}