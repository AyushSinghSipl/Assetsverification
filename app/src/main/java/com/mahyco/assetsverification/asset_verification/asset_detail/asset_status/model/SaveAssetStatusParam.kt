package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model

import com.google.gson.annotations.SerializedName

data class SaveAssetStatusParam(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

	@field:SerializedName("AssetQRId")
	val assetQRId: Int? = null,

	@field:SerializedName("Reason")
	val reason: String? = null
)
