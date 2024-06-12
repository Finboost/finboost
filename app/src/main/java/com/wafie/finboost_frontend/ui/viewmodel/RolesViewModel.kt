package com.wafie.finboost_frontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wafie.finboost_frontend.data.api.response.role.RolesItem
import com.wafie.finboost_frontend.data.api.response.role.RoleResponse
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import retrofit2.*
class RolesViewModel(): ViewModel() {

    private val _roles = MutableLiveData<List<RolesItem>>()
    val roles: LiveData<List<RolesItem>> = _roles

    fun fetchRoles() {
        val client = ApiConfig.getApiService().getRoles()
        client.enqueue(object : Callback<RoleResponse> {
            override fun onResponse(call: Call<RoleResponse>, response: Response<RoleResponse>) {
                if (response.isSuccessful) {
                    _roles.value = (response.body()?.data?.roles ?: emptyList()) as List<RolesItem>?
                    Log.d("RV", "value ${response.message()}")
                } else {
                    Log.e(TAG, "Error response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RoleResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}", t)
            }
        })
    }

    companion object {
        private const val TAG = "RoleViewModel"
    }

}