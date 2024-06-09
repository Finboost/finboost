package com.wafie.finboost_frontend.data.api.response.auth

import android.os.Message
import com.google.gson.annotations.SerializedName

data class SignUpResponse(
	val status: String? = null,

	val message: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("roleId")
	val roleId: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: Int? = null
)
