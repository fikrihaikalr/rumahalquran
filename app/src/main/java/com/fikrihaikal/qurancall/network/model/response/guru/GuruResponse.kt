package com.fikrihaikal.qurancall.network.model.response.guru

import com.google.gson.annotations.SerializedName

data class GuruResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse,

	@field:SerializedName("data")
	val data: List<DataItem>
)

data class DataItem(

	@field:SerializedName("photoPath")
	val photoPath: Any,

	@field:SerializedName("numberPhone")
	val numberPhone: String,

	@field:SerializedName("roles")
	val roles: List<String>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("status")
	val status: String
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
