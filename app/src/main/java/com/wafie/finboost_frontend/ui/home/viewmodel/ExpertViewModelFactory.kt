package com.wafie.finboost_frontend.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wafie.finboost_frontend.data.preferences.UserPreference

class ExpertViewModelFactory(private val userPreference: UserPreference): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpertViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpertViewModel(userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}