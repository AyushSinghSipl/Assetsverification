package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model

import com.google.gson.annotations.SerializedName

data class SaveStatusResponse(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("QRCode")
	val qRCode: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("AssetDescription")
	val assetDescription: String? = null,

	@field:SerializedName("ClassName")
	val className: String? = null,

	@field:SerializedName("AssetQRId")
	val assetQRId: Int? = null,

	@field:SerializedName("PlantName")
	val plantName: String? = null,

	@field:SerializedName("PlantCode")
	val plantCode: String? = null,

	@field:SerializedName("AssetCode")
	val assetCode: String? = null,

	@field:SerializedName("CapDt")
	val capDt: String? = null
)
