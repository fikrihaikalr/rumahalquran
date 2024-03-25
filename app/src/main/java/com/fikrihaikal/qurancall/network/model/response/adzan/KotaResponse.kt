package com.fikrihaikal.qurancall.network.model.response.adzan

import com.google.gson.annotations.SerializedName

data class KotaResponse(

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("id")
	val id: String
)
