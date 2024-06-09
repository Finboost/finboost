package com.wafie.finboost_frontend.ui.auth.signin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafie.finboost_frontend.data.api.response.auth.SignInResponse
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import com.wafie.finboost_frontend.data.model.UserModel
import com.wafie.finboost_frontend.data.preferences.UserPreference
import kotlinx.coroutines.launch

import retrofit2.*

class SignInViewModel(private val userPreference: UserPreference): ViewModel() {

    private val _signInResult = MutableLiveData<SignInResponse?>()
    val signInResult: LiveData<SignInResponse?> = _signInResult

    fun signIn(email: String, password: String) {
        val client = ApiConfig.getApiService().signIn(email, password)
        client.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "msg ${response.message()}")
                    val signInResponse = response.body()
                    _signInResult.value = signInResponse

                    // Save the session
                    viewModelScope.launch {
                        signInResponse?.let {
                            userPreference.saveSession(
                                UserModel(
                                    email = it.email ?: "",
                                    accessToken = it.accessToken ?: "",
                                    refreshToken = it.refreshToken ?: "",
                                    isLogin = true
                                )
                            )
                        }
                    }
                } else {
                    Log.e(TAG, "Error response code: ${response.code()}")
                    Log.e(TAG, "Error response message: ${response.errorBody()?.string()}")
                    _signInResult.value = SignInResponse(
                        status = "fail",
                        message = "Sign in failed: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}", t)
            }
        })
    }

    fun refreshToken() {
        val client = ApiConfig.getApiService().refreshToken()
        client.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful) {
                    val refreshResponse = response.body()
                    viewModelScope.launch {
                        refreshResponse?.let {
                            userPreference.saveSession(
                                UserModel(
                                    email = it.email ?: "",
                                    accessToken = it.accessToken ?: "",
                                    refreshToken = it.refreshToken ?: "",
                                    isLogin = true
                                )
                            )
                        }
                    }
                } else {
                    Log.e(TAG, "Error response code: ${response.code()}")
                    Log.e(TAG, "Error response message: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}", t)
            }
        })
    }
    companion object {
        private const val TAG = "SignInViewModel"
    }
}