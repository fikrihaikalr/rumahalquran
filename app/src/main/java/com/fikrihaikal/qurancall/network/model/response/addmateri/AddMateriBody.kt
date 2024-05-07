package com.fikrihaikal.qurancall.network.model.response.addmateri

import com.google.gson.annotations.SerializedName

data class AddMateriBody(
	val materiId: Int,
	val author: String,
	val title: String,
	val content: String
)
