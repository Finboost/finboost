package com.wafie.finboost_frontend.data.api.retrofit

import android.util.Log
import com.wafie.finboost_frontend.data.preferences.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val userPreference: UserPreference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val token = runBlocking { userPreference.getSession().first().accessToken }

        // Add the token to the request
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)

        // If the token is expired, refresh it
        if (response.code == 401) {
            synchronized(this) {
                val newToken = runBlocking {
                    val newAccessToken = refreshAccessToken()
                    if (newAccessToken.isNotEmpty()) {
                        userPreference.saveSession(
                            userPreference.getSession().first().copy(accessToken = newAccessToken)
                        )
                    }
                    newAccessToken
                }

                // Retry the request with the new token if refresh is successful
                if (newToken.isNotEmpty()) {
                    request = request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                    return chain.proceed(request)
                } else {
                    // Handle case where new token is not available
                    handleForbiddenError()
                }
            }
        } else if (response.code == 403) {
            // Handle forbidden error
            handleForbiddenError()
        }

        return response
    }

    private fun refreshAccessToken(): String {
        return try {
            val refreshResponse = ApiConfig.getApiService().refreshToken().execute()
            if (refreshResponse.isSuccessful) {
                Log.e(TAG, "RefreshToken: ${refreshResponse.message()}")
                refreshResponse.body()?.data?.accessToken ?: ""
            } else {
                Log.e(TAG, "Failed to refresh token, response code: ${refreshResponse.code()}")
                Log.e(TAG, "Failed to refresh token, response code: ${refreshResponse.message()}")
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    private fun handleForbiddenError() {
        // Implement your logic to handle 403 error here
        // For example, log out the user or show a message
        runBlocking {
            userPreference.logout()
        }
    }

    companion object {
        private const val TAG = "TokenInterceptor"
    }
}

