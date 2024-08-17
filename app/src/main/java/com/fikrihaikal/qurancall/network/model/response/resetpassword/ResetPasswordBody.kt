package com.fikrihaikal.qurancall.network.model.response.resetpassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordBody(

	@field:SerializedName("currentPassword")
	val currentPassword: String,
	@field:SerializedName("newPassword")
	val newPassword: String

)
