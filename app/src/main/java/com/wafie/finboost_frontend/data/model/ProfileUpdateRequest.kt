package com.wafie.finboost_frontend.data.model

data class ProfileUpdateRequest(
    val maritalStatus: String,
    val educationId: String,
    val workId: String,
    val investment: String,
    val insurance: String,
    val incomePerMonth: String
)
