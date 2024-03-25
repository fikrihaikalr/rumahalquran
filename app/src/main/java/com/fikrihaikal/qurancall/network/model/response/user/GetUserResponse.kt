package com.fikrihaikal.qurancall.network.model.response.user

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("photoPath")
	val photoPath: Any? = null,

	@field:SerializedName("roles")
	val roles: List<RolesItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class RolesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
