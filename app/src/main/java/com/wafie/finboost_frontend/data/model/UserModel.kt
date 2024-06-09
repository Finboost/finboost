package com.wafie.finboost_frontend.data.model

data class UserModel(
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val isLogin: Boolean
)
