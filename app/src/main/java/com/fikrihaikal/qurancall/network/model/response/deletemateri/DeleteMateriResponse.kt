package com.fikrihaikal.qurancall.network.model.response.deletemateri

import com.google.gson.annotations.SerializedName

data class DeleteMateriResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
