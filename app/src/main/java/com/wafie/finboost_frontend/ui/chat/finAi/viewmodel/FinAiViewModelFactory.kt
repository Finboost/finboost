package com.wafie.finboost_frontend.ui.chat.finAi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wafie.finboost_frontend.data.preferences.UserPreference

class FinAiViewModelFactory(private val userPreference: UserPreference): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinAiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinAiViewModel(userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}