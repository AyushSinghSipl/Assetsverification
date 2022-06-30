package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model

import com.google.gson.annotations.SerializedName

data class ScanQRParam(

	@field:SerializedName("filterValue")
	val filterValue: String? = null,

	@field:SerializedName("FilterOption")
	val filterOption: String? = "GetByQRCode_Plant"
)
