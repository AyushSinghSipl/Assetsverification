package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model

import com.google.gson.annotations.SerializedName

data class CheckUserValidParam(

	@field:SerializedName("UserCode")
	val userCode: String? = null,

	@field:SerializedName("RoleId")
	val roleId: Int? = null,

	@field:SerializedName("PlantId")
	val plantId: Int? = null
)
