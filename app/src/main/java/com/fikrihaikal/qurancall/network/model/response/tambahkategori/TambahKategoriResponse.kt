package com.fikrihaikal.qurancall.network.model.response.tambahkategori

import com.google.gson.annotations.SerializedName

data class TambahKategoriResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
