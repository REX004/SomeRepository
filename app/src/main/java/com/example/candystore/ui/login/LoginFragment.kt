package com.example.candystore.ui.login

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.candystore.data.api.AuthApi
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.databinding.FragmentLoginBinding
import com.example.candystore.ui.base.BaseFragment
import com.example.candystore.ui.enable
import com.example.candystore.ui.handleApiError
import com.example.candystore.ui.productspage.ProductsActivity
import com.example.candystore.ui.startNewActivity
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.ui.visible
import com.example.candystore.utils.Resource
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    private val PASS_MIN_CHARS = 5
    private var email: String = ""
    private var password: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visible(false)
        binding.singInBtn.enable(false)

        setUpStartingAnimations()
        handleLoginNetworkResponse()
        enterEmail()
        enterPass()
        login()
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): AuthRepository {
        val api = baseRetrofitInstance.buildApi(AuthApi::class.java)
        return AuthRepository(api, userPreferences)
    }

    private fun login() {
        binding.singInBtn.setOnClickListener {
            val userAuth = UserAuth(email, password)
            viewModel.login(userAuth)

        }
    }

    private fun setUpStartingAnimations() {
        val alphaAnim = AnimationUtils.loadAnimation(requireContext(), com.example.candystore.R.anim.top_animations)
        val loginTextFieldAnim = AnimationUtils.loadAnimation(requireContext(), com.example.candystore.R.anim.left_anim)
        val passTextFieldAnim = AnimationUtils.loadAnimation(requireContext(), com.example.candystore.R.anim.right_anim)

        binding.logoImageView.startAnimation(alphaAnim)
        binding.forgotPassBtn.startAnimation(alphaAnim)
        binding.guestBtn.startAnimation(alphaAnim)
        binding.hintTextviewGuestBtn.startAnimation(alphaAnim)
        binding.textInputLayoutLogin.startAnimation(loginTextFieldAnim)
        binding.textInputLayoutPassword.startAnimation(passTextFieldAnim)
        binding.singInBtn.startAnimation(alphaAnim)
    }

    private fun handleLoginNetworkResponse() {
        viewModel.authResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.data.token)
                        requireActivity().startNewActivity(ProductsActivity::class.java)
                    }
                }

                is Resource.Error -> handleApiError(it)

                else -> {}
            }
        })
    }
    private fun enterEmail() {
        binding.loginInputEditText.addTextChangedListener {
            val colorState: ColorStateList
            email = binding.loginInputEditText.text.toString().trim()

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                colorState = changeColorState(Color.GREEN)

            } else {
                colorState = changeColorState(Color.RED)
                binding.singInBtn.enable(false)
            }
            binding.textInputLayoutLogin.setBoxStrokeColorStateList(colorState)
        }
    }

    private fun enterPass() {
        binding.passwordInputEditText.addTextChangedListener {
            password = binding.passwordInputEditText.text.toString().trim()

            val colorState: ColorStateList
            if (password.length >= PASS_MIN_CHARS) {
                colorState = changeColorState(Color.GREEN)

                binding.singInBtn.enable(Patterns.EMAIL_ADDRESS.matcher(email).matches())

            } else {
                colorState = changeColorState(Color.RED)
                binding.singInBtn.enable(false)
            }

            binding.textInputLayoutPassword.setBoxStrokeColorStateList(colorState)
        }
    }

    private fun changeColorState(color: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_active),
                intArrayOf(R.attr.state_focused),
                intArrayOf(-R.attr.state_focused),
                intArrayOf(R.attr.state_hovered),
                intArrayOf(R.attr.state_enabled),
                intArrayOf(-R.attr.state_enabled)
            ),
            intArrayOf(
                color,
                color,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
            )
        )
    }
}