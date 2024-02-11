package com.example.candystore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.candystore.data.UserPreferences
import com.example.candystore.ui.login.LoginActivity
import com.example.candystore.ui.productspage.ProductsActivity
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                true
            }
        }
        checkToken()
    }

    private fun checkToken() {
        val userPreferences = UserPreferences(this)
        lifecycleScope.launch {
            userPreferences.authToken.collect { token ->
                if (token.equals("") || token == null) {
                    startNewActivity(LoginActivity::class.java)
                } else {
                    startNewActivity(ProductsActivity::class.java)
                }
            }
        }
    }
}