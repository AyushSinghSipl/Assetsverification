package com.mahyco.assetsverification.login.model

import com.google.gson.annotations.SerializedName

data class LoginParam(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
