package com.wafie.finboost_frontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wafie.finboost_frontend.data.api.response.edu.EducationsItem
import com.wafie.finboost_frontend.data.api.response.work.WorkResponse
import com.wafie.finboost_frontend.data.api.response.work.WorksItem
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorksViewModel: ViewModel() {
    private val _works =  MutableLiveData<List<WorksItem>>()
    val works: LiveData<List<WorksItem>> = _works

    fun fetchWorks() {
        val client = ApiConfig.getApiService().getWorks()
        client.enqueue(object : Callback<WorkResponse> {
            override fun onResponse(call: Call<WorkResponse>, response: Response<WorkResponse>) {
                if (response.isSuccessful) {
                    _works.value = (response.body()?.data?.works ?: emptyList()) as List<WorksItem>?
                }
            }

            override fun onFailure(call: Call<WorkResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}", t)
            }

        })
    }

    companion object {
        private const val TAG = "WorksViewModel"
    }
}