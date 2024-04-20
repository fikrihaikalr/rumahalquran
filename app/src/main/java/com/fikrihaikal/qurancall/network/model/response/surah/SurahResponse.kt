package com.fikrihaikal.qurancall.network.model.response.surah

import com.google.gson.annotations.SerializedName

data class SurahResponse(

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

	@field:SerializedName("surahName")
	val surahName: String,

	@field:SerializedName("translateId")
	val translateId: String,

	@field:SerializedName("number")
	val number: String,

	@field:SerializedName("audioUrl")
	val audioUrl: String,

	@field:SerializedName("id")
	val id: String
)
