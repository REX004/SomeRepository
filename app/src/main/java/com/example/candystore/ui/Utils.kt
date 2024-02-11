package com.example.candystore.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.candystore.R
import com.example.candystore.ui.login.LoginFragment
import com.example.candystore.utils.Resource
import com.google.android.material.snackbar.Snackbar


fun<A: Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(isEnable: Boolean) {
    isEnabled = isEnable
    alpha = if (isEnable) {
        1.0F
    } else {
        0.5F
    }
}

fun View.snackbar(message:String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction(resources.getString(R.string.retry)) {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Error,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            resources.getString(R.string.internet_error),
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar(resources.getString(R.string.login_incorrect))
            } else {

            }
        } else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}