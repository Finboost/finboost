package com.wafie.finboost_frontend.data.api.response.finai

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

	@field:SerializedName("suggested_questions")
	val suggestedQuestions: List<String>? = null,

	@field:SerializedName("response")
	val answer: String? = null
)
