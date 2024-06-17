package com.wafie.finboost_frontend.data.api.response.edu

import com.google.gson.annotations.SerializedName

data class EducationResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("educations")
	val educations: List<EducationsItem?>? = null
)

data class EducationsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
