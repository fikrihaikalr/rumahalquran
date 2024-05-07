package com.fikrihaikal.qurancall.network.model.response.detailcontentmateri

import com.google.gson.annotations.SerializedName

data class DetailContentMateriResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse,

	@field:SerializedName("data")
	val data: Data
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String
)
