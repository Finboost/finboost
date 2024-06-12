package com.wafie.finboost_frontend.data.api.retrofit

import com.wafie.finboost_frontend.data.api.response.auth.SignInResponse
import com.wafie.finboost_frontend.data.api.response.auth.SignUpResponse
import com.wafie.finboost_frontend.data.api.response.chat.FinAiResponse
import com.wafie.finboost_frontend.data.api.response.role.RoleResponse
import com.wafie.finboost_frontend.data.api.response.role.RolesItem
import com.wafie.finboost_frontend.data.api.response.users.ExpertDetailResponse
import com.wafie.finboost_frontend.data.api.response.users.UserResponse
import com.wafie.finboost_frontend.data.model.SignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

     //FinAi Chat
     @FormUrlEncoded
     @POST("chats/ai/predict")
     fun sendChat(
         @Header("Authorization") token: String,
         @Field("question") question : String
     ) : Call<FinAiResponse>

     //Get User where role= Expert
     @GET("users?role=Expert")
     fun getUserRole(
         @Header("Authorization") token: String,
     ): Call<UserResponse>

     //Get Expert Id
     @GET("users/{userId}")
     fun getExpertId(
         @Header("Authorization") token: String,
         @Path("userId") userId : String
     ): Call<ExpertDetailResponse>
}