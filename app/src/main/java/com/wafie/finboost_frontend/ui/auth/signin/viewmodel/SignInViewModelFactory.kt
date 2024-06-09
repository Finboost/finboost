package com.wafie.finboost_frontend.ui.auth.signin.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.wafie.finboost_frontend.data.preferences.UserPreference


class SignInViewModelFactory(private val userPreference: UserPreference) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}