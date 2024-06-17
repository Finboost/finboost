package com.wafie.finboost_frontend.data.api.response.users

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Profile(

	@field:SerializedName("educationId")
	val educationId: Any? = null,

	@field:SerializedName("about")
	val about: String? = null,

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

	@field: SerializedName("investment")
	val investment: String? = null,

	@field: SerializedName("insurance")
	val insurance: String? = null,

	@field: SerializedName("totalSaving")
	val totalSaving: Int? = null,

	@field: SerializedName("incomePerMonth")
	val incomePerMonth: Int? = null,

	@field: SerializedName("totalDebt")
	val totalDebt: Int? = null

)
data class Role(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Data(

	@field:SerializedName("users")
	val users: List<UsersItem?>? = null
)

data class UsersItem(

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("role")
	val role: Role? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("profile")
	val profile: Profile? = null,
)
