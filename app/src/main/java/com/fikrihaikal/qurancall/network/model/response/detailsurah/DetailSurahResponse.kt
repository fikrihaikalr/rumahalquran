package com.fikrihaikal.qurancall.network.model.response.detailsurah

import com.google.gson.annotations.SerializedName

data class DetailSurahResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse,

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("surahName")
	val surahName: String,

	@field:SerializedName("translateId")
	val translateId: String,

	@field:SerializedName("number")
	val number: String,

	@field:SerializedName("audioUrl")
	val audioUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("ayahData")
	val ayahData: List<AyahDataItem>
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class AyahDataItem(

	@field:SerializedName("ayah")
	val ayah: String,

	@field:SerializedName("latin")
	val latin: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("arab")
	val arab: String
)
