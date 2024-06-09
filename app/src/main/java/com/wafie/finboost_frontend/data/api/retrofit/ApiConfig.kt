package com.wafie.finboost_frontend.data.api.retrofit

import com.wafie.finboost_frontend.BuildConfig
import com.wafie.finboost_frontend.data.preferences.UserPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiServices {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(TokenInterceptor(userPreference))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev---finboost-backend-rtalegstna-et.a.run.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiServices::class.java)
    }
}