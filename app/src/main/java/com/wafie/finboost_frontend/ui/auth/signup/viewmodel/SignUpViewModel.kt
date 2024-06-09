package com.wafie.finboost_frontend.ui.auth.signup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafie.finboost_frontend.data.api.response.auth.SignUpResponse
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.*

class SignUpViewModel(): ViewModel() {

    //Sign Up
    private val _signUpResult = MutableLiveData<SignUpResponse>()
    val signUpResult: LiveData<SignUpResponse> = _signUpResult

    fun signUp(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        gender: String,
        roleId: String,
        age: Int
    ) {
        viewModelScope.launch {
            Log.d(TAG, "Request Data - fullName: $name, email: $email, password: $password, phoneNumber: $phoneNumber, gender: $gender, roleId: $roleId, age: $age")
            val client = ApiConfig.getApiService().signUp(name, email, gender, age, phoneNumber, password, roleId)
            client.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "msg ${response.message()}")
                        _signUpResult.value = response.body()
                    } else {
                        Log.e(TAG, "Error response code: ${response.code()}")
                        Log.e(TAG, "Error response message: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.e(TAG, "API call failed: ${t.message}", t)
                }
            })
        }
    }

    companion object {
        private const val  TAG = "SignUpViewModel"
    }
}