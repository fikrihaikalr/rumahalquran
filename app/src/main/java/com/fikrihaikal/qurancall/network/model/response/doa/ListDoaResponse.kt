 package com.fikrihaikal.qurancall.network.model.response.doa

import com.google.gson.annotations.SerializedName

data class ListDoaResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("doaName")
	val doaName: String? = null,

	@field:SerializedName("translateDoa")
	val translateDoa: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("arabDoa")
	val arabDoa: Any? = null
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
