package com.wafie.finboost_frontend.data.api.response.users

import com.google.gson.annotations.SerializedName

data class ExpertDetailResponse(

	@field:SerializedName("data")
	val data: ExpertData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class User(


	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("role")
	val role: Role? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("profile")
	val profile: DetailProfile? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,
)

data class DetailProfile(


	@field:SerializedName("educationId")
	val educationId: Any? = null,

	@field:SerializedName("certifiedStatus")
	val certifiedStatus: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("maritalStatus")
	val maritalStatus: Any? = null,

	@field:SerializedName("workId")
	val workId: Any? = null,

)

data class ExpertData(

	@field:SerializedName("user")
	val user: User? = null
)
