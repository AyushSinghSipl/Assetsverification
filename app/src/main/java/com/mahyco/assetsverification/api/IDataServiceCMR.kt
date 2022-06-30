package com.mahyco.assetsverification.api

import com.google.gson.JsonObject
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.*
import com.mahyco.assetsverification.login.model.LoginParam
import com.mahyco.assetsverification.login.model.LoginResponseModel
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface IDataServiceCMR {


    @POST("customToken/customToken")
    fun callLogin(@Body loginParam: LoginParam): Call<LoginResponseModel>

    @POST("Assets/getQRCode")
    fun getQrReport(@Body scanQRParam: ScanQRParam): Call<ScanQRResult>

    @POST("Assets/saveAssetStatus")
    fun saveAssetStatus(@Body saveAssetStatusParam: SaveAssetStatusParam): Call<SaveStatusResponse>

    @POST("Assets/checkUserValid")
    fun checkUserValid(@Body checkUserValidParam: CheckUserValidParam): Call<CheckUserValidResponse>


}