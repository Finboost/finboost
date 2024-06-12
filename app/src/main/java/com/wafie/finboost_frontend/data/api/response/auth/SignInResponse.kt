package com.wafie.finboost_frontend.data.api.response.auth

import com.google.gson.annotations.SerializedName

data class SignInResponse(
	val status: String? = null,
	val message: String? = null,
	@SerializedName("data") val data: SignInData? = null
)

data class SignInData(
	@SerializedName("fullName") val fullName: String? = null,
	@SerializedName("accessToken") val accessToken: String? = null,
	@SerializedName("refreshToken") val refreshToken: String? = null,
	@SerializedName("email") val email: String? = null
)
