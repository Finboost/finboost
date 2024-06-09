package com.wafie.finboost_frontend.data.api.retrofit

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
                    userPreference.saveSession(
                        userPreference.getSession().first().copy(accessToken = newAccessToken)
                    )
                    newAccessToken
                }

                // Retry the request with the new one
                request = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $newToken")
                    .build()

                return chain.proceed(request)
            }
        }

        return response
    }

    private fun refreshAccessToken(): String {
        //call the endpoint here
        val refreshResponse = ApiConfig.getApiService().refreshToken().execute()
        return refreshResponse.body()?.data?.accessToken ?: ""
    }
}