package com.example.candystore.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.candystore.data.UserPreferences
import com.example.candystore.data.api.BaseRetrofitInstance
import com.example.candystore.data.repository.BaseRepository
import com.example.candystore.ui.viewmodels.ViewModelProviderFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: ViewModel, B: ViewBinding, R: BaseRepository> : Fragment() {
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected lateinit var userPreferences: UserPreferences
    protected lateinit var baseRetrofitInstance: BaseRetrofitInstance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        baseRetrofitInstance = BaseRetrofitInstance()
        userPreferences = UserPreferences(requireContext())

        lifecycleScope.launch { userPreferences.authToken.first() }

        val factory = ViewModelProviderFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]
        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): B

    abstract fun getFragmentRepository(): R
}