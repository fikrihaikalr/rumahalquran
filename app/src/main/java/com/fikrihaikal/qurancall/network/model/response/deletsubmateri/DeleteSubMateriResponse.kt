package com.fikrihaikal.qurancall.network.model.response.deletsubmateri

import com.google.gson.annotations.SerializedName

data class DeleteSubMateriResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
