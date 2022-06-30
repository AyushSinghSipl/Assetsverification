package com.mahyco.assetsverification.login.model

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("Role")
	val role: String? = null,

	@field:SerializedName("LoginId")
	val loginId: Int? = null,

	@field:SerializedName("returnmsg")
	val returnmsg: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("returnURL")
	val returnURL: Any? = null,

	@field:SerializedName("expires_in")
	val expiresIn: String? = null,

	@field:SerializedName("RoleId")
	val roleId: Int? = null,

	@field:SerializedName("EmpCode")
	val empCode: String? = null,

	@field:SerializedName("EmpName")
	val empName: String? = null
)
