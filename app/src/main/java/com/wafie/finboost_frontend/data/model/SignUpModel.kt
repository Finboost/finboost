package com.wafie.finboost_frontend.data.model

data class SignUpData(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val gender: String,
    val roleId: String,
    val age: Int
)