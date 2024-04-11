package com.fikrihaikal.qurancall.network.model.response.detailguru

import com.google.gson.annotations.SerializedName

data class DetailGuruResponse(

	@field:SerializedName("messageResponse")
	val messageResponse: MessageResponse,

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("photoPath")
	val photoPath: Any,

	@field:SerializedName("roles")
	val roles: List<String>,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

data class MessageResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
