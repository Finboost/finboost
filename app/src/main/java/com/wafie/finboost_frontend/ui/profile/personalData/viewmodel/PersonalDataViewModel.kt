package com.wafie.finboost_frontend.ui.profile.personalData.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafie.finboost_frontend.data.api.response.profile.ProfileResponse
import com.wafie.finboost_frontend.data.api.response.profile.Profile
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import com.wafie.finboost_frontend.data.model.ProfileUpdateRequest
import com.wafie.finboost_frontend.data.preferences.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalDataViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val token = runBlocking { userPreference.getSession().first().accessToken }

    private val _userProfileById = MutableLiveData<Profile?>()
    val userProfileById: LiveData<Profile?> = _userProfileById

    fun getUserProfileById(userId: String) {
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getUserProfileById("Bearer $token", userId)
            client.enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "msg ${response.body()?.data}")
                        _userProfileById.value = response.body()?.data?.profile
                    } else {
                        Log.e(TAG, "Error response code: ${response.code()}")
                        Log.e(TAG, "Error response message: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Log.e(TAG, "API call failed: ${t.message}", t)
                }
            })
        }
    }

    fun updateUserProfileById(
        userId: String,
        maritalStatus: String,
        educationId: String,
        workId: String,
        investment: String,
        insurance: String,
        incomePerMonth: String,
        onComplete: (Boolean) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = userPreference.getSession().first().accessToken
                val call = ApiConfig.getApiService().updateUserProfile(
                    "Bearer $token",
                    userId,
                    ProfileUpdateRequest(
                        maritalStatus,
                        educationId,
                        workId,
                        investment,
                        insurance,
                        incomePerMonth
                    )
                )

                call.enqueue(object : Callback<ProfileResponse> {
                    override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                        if (response.isSuccessful) {
                            onComplete(true)
                        } else {
                            onError(response.errorBody()?.string() ?: "Unknown error")
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        onError(t.message ?: "Unknown error")
                    }
                })
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        private const val TAG = "PersonalDataViewModel"
    }
}
