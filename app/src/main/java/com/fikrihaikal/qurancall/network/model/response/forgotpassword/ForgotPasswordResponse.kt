package com.fikrihaikal.qurancall.network.model.response.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
