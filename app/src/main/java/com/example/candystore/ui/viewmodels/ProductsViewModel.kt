package com.example.candystore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.candystore.data.models.AuthResponse
import com.example.candystore.data.repository.UserRepository
import com.example.candystore.utils.Resource
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _user: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val user: LiveData<Resource<Unit>>
        get() = _user


    fun logout(token: String) = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.postValue(repository.logout(token))
    }
}