package com.wafie.finboost_frontend.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafie.finboost_frontend.data.api.response.auth.SignUpResponse
import com.wafie.finboost_frontend.data.api.response.users.ExpertDetailResponse
import com.wafie.finboost_frontend.data.api.response.users.User
import com.wafie.finboost_frontend.data.api.response.users.UserResponse
import com.wafie.finboost_frontend.data.api.response.users.UsersItem
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.ui.auth.signup.viewmodel.SignUpViewModel
import com.wafie.finboost_frontend.ui.chat.finAi.viewmodel.FinAiViewModel
import com.wafie.finboost_frontend.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpertViewModel(private val userPreference: UserPreference): ViewModel() {

    private  val token = runBlocking { userPreference.getSession().first().accessToken }

    private val _expertList = MutableLiveData<List<UsersItem>?>()
    val expertList: LiveData<List<UsersItem>?> = _expertList

    private val _selectedExpertById = MutableLiveData<User?>()
    val selectedExpertById: LiveData<User?> = _selectedExpertById

    fun getListExpert(
    ) {
        viewModelScope.launch {
            val client = ApiConfig.getApiService()
                .getUserRole("Bearer $token")

            Log.d(TAG, "token: Bearer $token")
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "msg ${response.message()}")
                        _expertList.value = response.body()?.data?.users as List<UsersItem>?
                    } else {
                        Log.e(TAG, "Error response code: ${response.code()}")

                        Log.e(
                            TAG,
                            "Error response message: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e(TAG, "API call failed: ${t.message}", t)
                }
            })
        }
    }

    fun getExpertById(userId: String) {
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getExpertId("Bearer $token", userId)
            client.enqueue(object : Callback<ExpertDetailResponse> {
                override fun onResponse(
                    call: Call<ExpertDetailResponse>,
                    response: Response<ExpertDetailResponse>
                ) {
                    if(response.isSuccessful) {
                        Log.d(TAG, "responseSuccess ${response.body()?.data?.user}")
                        _selectedExpertById.value = response.body()?.data?.user

                    }
                }

                override fun onFailure(call: Call<ExpertDetailResponse>, t: Throwable) {
                    Log.e(TAG, "API call failed: ${t.message}", t)
                }

            })
        }
    }

    companion object {
        private const val TAG = "ExpertViewModel"
    }
}
