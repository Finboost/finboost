package com.wafie.finboost_frontend.data.api.response.work

import com.google.gson.annotations.SerializedName

data class WorkResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class WorksItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Data(

	@field:SerializedName("works")
	val works: List<WorksItem?>? = null
)
