package com.wafie.finboost_frontend.data.api.response.role

import com.google.gson.annotations.SerializedName

data class RoleResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(
	@field:SerializedName("roles")
	val roles: List<RolesItem?>? = null
)

data class RolesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
