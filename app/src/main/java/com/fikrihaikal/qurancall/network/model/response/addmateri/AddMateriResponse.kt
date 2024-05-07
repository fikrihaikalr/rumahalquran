package com.fikrihaikal.qurancall.network.model.response.addmateri

import com.google.gson.annotations.SerializedName

data class AddMateriResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
