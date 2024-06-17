package com.wafie.finboost_frontend.data.api.retrofit

import com.wafie.finboost_frontend.data.api.response.auth.SignInResponse
import com.wafie.finboost_frontend.data.api.response.auth.SignUpResponse
import com.wafie.finboost_frontend.data.api.response.edu.EducationResponse
import com.wafie.finboost_frontend.data.api.response.finai.FinAiResponse
import com.wafie.finboost_frontend.data.api.response.profile.ProfileResponse
import com.wafie.finboost_frontend.data.api.response.role.RoleResponse
import com.wafie.finboost_frontend.data.api.response.users.ExpertDetailResponse
import com.wafie.finboost_frontend.data.api.response.users.UserResponse
import com.wafie.finboost_frontend.data.api.response.work.WorkResponse
import com.wafie.finboost_frontend.data.model.ProfileUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    //getAllRoles
    @GET("roles")
    fun getRoles(): Call<RoleResponse>

    //getAll Education
    @GET("educations")
    fun getEducations(): Call<EducationResponse>

    //getAll works
    @GET("works")
    fun getWorks(): Call<WorkResponse>

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
         @Field("prompt") question : String
     ) : Call<FinAiResponse>

     //Get User by Role
     @GET("users")
     fun getUserRole(
         @Header("Authorization") token: String,
         @Query("role") role: String
     ): Call<UserResponse>

     //Get Expert Id
     @GET("users/{userId}")
     fun getExpertId(
         @Header("Authorization") token: String,
         @Path("userId") userId : String
     ): Call<ExpertDetailResponse>

     //Get User Profile
     @GET("users/{userId}/profile")
     fun getUserProfileById(
         @Header("Authorization") token: String,
         @Path("userId") userId: String
     ) : Call<ProfileResponse>

     //Get AI question suggestion after post the data
     @FormUrlEncoded
     @POST("chats/ai/suggestions")
     fun getAiQuestionSuggestion(
         @Header("Authorization") token: String,
         @Field("total_questions") totalQuestion: Int,
         @Field("user_input") userInput: String
     ) : Call<FinAiResponse>

    // Update User Profile using PUT method
     @PATCH("users/{userId}/profile")
     fun updateUserProfile(
         @Header("Authorization") token: String,
         @Path("userId") userId: String,
         @Body profileData: ProfileUpdateRequest
     ): Call<ProfileResponse>
}