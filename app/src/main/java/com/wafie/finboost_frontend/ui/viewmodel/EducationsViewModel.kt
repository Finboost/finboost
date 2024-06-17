package com.wafie.finboost_frontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wafie.finboost_frontend.data.api.response.edu.EducationResponse
import com.wafie.finboost_frontend.data.api.response.edu.EducationsItem
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EducationsViewModel: ViewModel() {

    private val _edu = MutableLiveData<List<EducationsItem>>()
    val edu: LiveData<List<EducationsItem>> = _edu

    fun fetchEducation() {
        val client = ApiConfig.getApiService().getEducations()
        client.enqueue(object : Callback<EducationResponse> {
            override fun onResponse(
                call: Call<EducationResponse>,
                response: Response<EducationResponse>
            ) {
                if (response.isSuccessful) {
                    _edu.value = (response.body()?.data?.educations ?: emptyList()) as List<EducationsItem>?
                }
            }

            override fun onFailure(call: Call<EducationResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}", t)
            }
        })
    }

    companion object {
        private const val  TAG = "EduViewModel"
    }
}