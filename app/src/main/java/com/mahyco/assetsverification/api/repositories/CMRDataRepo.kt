package com.mahyco.cmr_app.repositories

import com.google.gson.JsonObject
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.*
import com.mahyco.assetsverification.login.model.LoginParam
import com.mahyco.assetsverification.login.model.LoginResponseModel
import io.reactivex.Observable
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CMRDataRepo {


    fun callLoginAPi(loginParam: LoginParam): Observable<LoginResponseModel>
    fun getAssetDetail(scanQRParam: ScanQRParam): Observable<ScanQRResult>
    fun SaveAssetStatus(saveAssetStatusParam: SaveAssetStatusParam): Observable<SaveStatusResponse>
    fun checkUserValid(checkUserValidParam: CheckUserValidParam): Observable<CheckUserValidResponse>


}