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
import com.wafie.finboost_frontend.utils.Utils.convertFileToMultipart
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.ui.auth.signup.viewmodel.SignUpViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PersonalDataViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val token = runBlocking { userPreference.getSession().first().accessToken }

    private val _userProfileById = MutableLiveData<Profile?>()
    val userProfileById: LiveData<Profile?> = _userProfileById

    private val _updatedProfile = MutableLiveData<ProfileResponse?>()
    val updatedProfile: LiveData<ProfileResponse?> = _updatedProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
        about: String
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
                        incomePerMonth,
                        about
                    )
                )
                _isLoading.value = false
                call.enqueue(object : Callback<ProfileResponse> {
                    override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                        if (response.isSuccessful) {
                            _updatedProfile.value = response.body()
                        } else {
                            _updatedProfile.value = null
                            _updatedProfile.value = ProfileResponse(status = "fail", message = response.errorBody()?.string())
                            Log.e(TAG, "Error response code: ${response.code()}")
                            Log.e(TAG, "Error response message: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Log.e(TAG, "API call failed: ${t.message}", t)
                    }
                })
            } catch (e: Exception) {
                Log.e(TAG, "Andorid Execption Error: ${e.message}", e)
            }
        }
    }

    fun updateUserPhoto(userId: String, file: File, onComplete: (Boolean) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val token = userPreference.getSession().first().accessToken

                val mimeType = when (file.extension.toLowerCase()) {
                    "jpg", "jpeg" -> "image/jpeg"
                    "png" -> "image/png"
                    else -> {
                        onError("Invalid file type. Only JPG, PNG are allowed!")
                        return@launch
                    }
                }

                val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("avatar", file.name, requestBody)
                val avatarName = file.name.toRequestBody("text/plain".toMediaTypeOrNull())

                val call = ApiConfig.getApiService().updateUserPhoto("Bearer $token", userId, body, avatarName)

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            Log.d(TAG, "Photo updated successfully")
                            onComplete(true)
                        } else {
                            val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                            Log.e(TAG, "Error response message: ${response.code()}")
                            Log.e(TAG, "Error with mess: ${response.body()}")
                            onError(errorMsg)
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e(TAG, "API call failed: ${t.message}", t)
                        onError(t.message ?: "Unknown error")
                    }
                })
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}", e)
                onError(e.message ?: "Unknown error")
            }
        }
    }


    companion object {
        private const val TAG = "PersonalDataViewModel"
    }
}
