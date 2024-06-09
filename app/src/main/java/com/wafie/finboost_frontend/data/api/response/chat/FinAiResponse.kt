package com.wafie.finboost_frontend.data.api.response.chat

import com.google.gson.annotations.SerializedName

data class FinAiResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("answer")
	val answer: String? = null
)
