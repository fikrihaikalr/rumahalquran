package com.fikrihaikal.qurancall.network.model.response.menumateri

import com.google.gson.annotations.SerializedName

data class MenuMateriResponse(

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

	@field:SerializedName("jumlahMateri")
	val jumlahMateri: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String
)
