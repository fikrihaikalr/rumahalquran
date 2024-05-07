package com.fikrihaikal.qurancall.network.model.response.submenumateri

import com.google.gson.annotations.SerializedName

data class SubMenuMateriResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse,

	@field:SerializedName("data")
	val data: List<DataItem>
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String
)
