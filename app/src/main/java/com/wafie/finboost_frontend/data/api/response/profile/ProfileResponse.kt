package com.wafie.finboost_frontend.data.api.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Work(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Profile(

	@field:SerializedName("insurance")
	val insurance: String? = null,

	@field:SerializedName("totalSaving")
	val totalSaving: String? = null,

	@field:SerializedName("education")
	val education: Education? = null,

	@field:SerializedName("certifiedStatus")
	val certifiedStatus: Any? = null,

	@field:SerializedName("work")
	val work: Work? = null,

	@field:SerializedName("totalDebt")
	val totalDebt: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("investment")
	val investment: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("maritalStatus")
	val maritalStatus: String? = null,

	@field:SerializedName("incomePerMonth")
	val incomePerMonth: String? = null
)

data class Education(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Data(

	@field:SerializedName("profile")
	val profile: Profile? = null
)
