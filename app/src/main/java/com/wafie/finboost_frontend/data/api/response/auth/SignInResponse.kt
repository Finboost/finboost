package com.wafie.finboost_frontend.data.api.response.auth

import com.google.gson.annotations.SerializedName

data class SignInResponse(

	val status: String? = null,

	val message: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
