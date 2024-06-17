package com.wafie.finboost_frontend.ui.profile.personalData.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wafie.finboost_frontend.data.preferences.UserPreference

class PersonalDataViewModelFactory(private val userPreference: UserPreference): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonalDataViewModel(userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}