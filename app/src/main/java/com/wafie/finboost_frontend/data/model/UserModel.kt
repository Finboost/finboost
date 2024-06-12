package com.wafie.finboost_frontend.data.model

data class UserModel(
    val id: String,
    val fullName: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val isLogin: Boolean
)
