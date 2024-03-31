package com.fikrihaikal.qurancall.network.model.response.doa

import com.google.gson.annotations.SerializedName

	data class DoaResponse(

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

		@field:SerializedName("doaName")
		val doaName: String,

		@field:SerializedName("id")
		val id: String
	)
