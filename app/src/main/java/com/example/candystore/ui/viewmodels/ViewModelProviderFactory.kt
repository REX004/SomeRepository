package com.example.candystore.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.data.repository.BaseRepository
import com.example.candystore.data.repository.UserRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory(
    private val repository: BaseRepository
) : ViewModelProvider.Factory {
    private val VM_NOT_FOUND = "ViewModelClass not found"
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return when {
           modelClass.isAssignableFrom(
               AuthViewModel::class.java
           ) -> AuthViewModel(repository as AuthRepository) as T
           modelClass.isAssignableFrom(
               ProductsViewModel::class.java
           ) -> ProductsViewModel(repository as UserRepository) as T
           else -> throw IllegalArgumentException(VM_NOT_FOUND)
       }
    }
}