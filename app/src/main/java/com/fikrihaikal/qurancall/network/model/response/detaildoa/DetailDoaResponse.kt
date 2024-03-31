package com.fikrihaikal.qurancall.network.model.response.detaildoa

import com.google.gson.annotations.SerializedName

data class DetailDoaResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse,

	@field:SerializedName("doa")
	val doa: List<DoaItem>
)

data class DoaItem(

	@field:SerializedName("doaName")
	val doaName: String,

	@field:SerializedName("translateDoa")
	val translateDoa: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("arabDoa")
	val arabDoa: String
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
