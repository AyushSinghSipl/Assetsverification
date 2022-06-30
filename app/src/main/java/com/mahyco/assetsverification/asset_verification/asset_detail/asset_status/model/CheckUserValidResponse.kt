package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model

import com.google.gson.annotations.SerializedName

data class CheckUserValidResponse(

	@field:SerializedName("Comment")
	val comment: String? = null,

	@field:SerializedName("ResultFlag")
	val resultFlag: Boolean? = null,

	@field:SerializedName("Code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
