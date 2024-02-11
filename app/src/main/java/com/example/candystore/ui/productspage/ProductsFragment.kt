package com.example.candystore.ui.productspage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.candystore.R
import com.example.candystore.data.api.UserApi
import com.example.candystore.data.models.User
import com.example.candystore.data.repository.UserRepository
import com.example.candystore.databinding.FragmentProductsBinding
import com.example.candystore.ui.base.BaseFragment
import com.example.candystore.ui.login.LoginActivity
import com.example.candystore.ui.startNewActivity
import com.example.candystore.ui.viewmodels.ProductsViewModel
import com.example.candystore.ui.visible
import com.example.candystore.utils.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProductsFragment : BaseFragment<ProductsViewModel, FragmentProductsBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)

        val token = runBlocking { userPreferences.authToken.first() }

        binding.logoutBtn.setOnClickListener {
            viewModel.logout("Bearer $token")
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    runBlocking {
                        userPreferences.saveAuthToken("")
                    }
                    val intent = Intent (activity,LoginActivity::class.java)
                    activity?.startActivity(intent)

                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                }
            }
        })

    }

    override fun getViewModel(): Class<ProductsViewModel> = ProductsViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProductsBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() : UserRepository {

        val api = baseRetrofitInstance.buildApi(UserApi::class.java)
        return UserRepository(api)
    }

}