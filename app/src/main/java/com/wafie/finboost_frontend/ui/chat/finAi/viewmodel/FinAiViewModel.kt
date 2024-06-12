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



class FinAiViewModel(private val userPreference: UserPreference) : ViewModel() {
    private val _question = MutableLiveData<String>()
    val question: LiveData<String> = _question

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _aiAnswer = MutableLiveData<String>()
    val aiAnswer: LiveData<String> = _aiAnswer

    fun sendQuestion(question: String) {
        _question.value = question

        viewModelScope.launch {
            val token = runBlocking { userPreference.getSession().first().accessToken }
            val client = ApiConfig.getApiService().sendChat("Bearer $token", question)
            client.enqueue(object : Callback<FinAiResponse> {
                override fun onResponse(call: Call<FinAiResponse>, response: Response<FinAiResponse>) {
                    Log.d(TAG, "Request Data: $question")
                    Log.d(TAG, "token: $token")
                    if (response.isSuccessful) {
                        response?.body()?.data?.answer?.let { aiAnswer ->
                            _aiAnswer.value = aiAnswer
                        }
                    } else if (response.code() == 403) {
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
