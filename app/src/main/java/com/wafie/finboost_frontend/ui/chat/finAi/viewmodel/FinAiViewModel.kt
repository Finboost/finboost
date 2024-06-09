package com.wafie.finboost_frontend.ui.chat.finAi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafie.finboost_frontend.data.api.response.chat.FinAiResponse
import com.wafie.finboost_frontend.data.api.retrofit.ApiConfig
import com.wafie.finboost_frontend.data.preferences.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import retrofit2.*

class FinAiViewModel(private val userPreference: UserPreference): ViewModel() {
    private val _question = MutableLiveData<List<String>>()
    val question: LiveData<List<String>> = _question

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val messageList = mutableListOf<String>()

    fun sendQuestion(question: String) {
        _question.value = _question.value.orEmpty() + question

        viewModelScope.launch {
            val token = runBlocking { userPreference.getSession().first().accessToken }
            val client = ApiConfig.getApiService().sendChat("Bearer $token", question)
            client.enqueue(object : Callback<FinAiResponse> {
                override fun onResponse(
                    call: Call<FinAiResponse>,
                    response: Response<FinAiResponse>
                ) {
                    Log.d(TAG, "Request Data: $question")
                    Log.d(TAG, "token: $token")
                    if (response.isSuccessful) {
                        response?.body()?.data?.answer?.let {
                            messageList.add(it)
                            _question.value= messageList
                        }
                    } else if(response.code() == 403) {
                        _error.value = "Failed to get response"
                    }
                }

                override fun onFailure(call: Call<FinAiResponse>, t: Throwable) {
                    _error.value = t.message
                }

            })
        }
    }

    companion object {
        private const val TAG = "FinAiViewModel"
    }
}