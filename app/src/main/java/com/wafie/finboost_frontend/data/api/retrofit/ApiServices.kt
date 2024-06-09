package com.wafie.finboost_frontend.data.api.retrofit

import com.wafie.finboost_frontend.data.api.response.auth.SignInResponse
import com.wafie.finboost_frontend.data.api.response.auth.SignUpResponse
import com.wafie.finboost_frontend.data.api.response.role.RoleResponse
import com.wafie.finboost_frontend.data.api.response.role.RolesItem
import com.wafie.finboost_frontend.data.model.SignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    //getAllRoles
    @GET("roles")
    fun getRoles(): Call<RoleResponse>

    //SignUp
    @FormUrlEncoded
    @POST("auth/signup")
     fun signUp(
        @Field("fullName") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("phoneNumber") phoneNumber: String,
        @Field("password") password: String,
        @Field("roleId") roleId: String,

    ): Call<SignUpResponse>

     //SignIn
     @FormUrlEncoded
     @POST("auth/signin")
     fun signIn(
         @Field("email") email: String,
         @Field("password") password: String
     ) : Call<SignInResponse>

     //RefreshToken
     @GET("token/refresh")
     fun refreshToken() :Call <SignInResponse>
}