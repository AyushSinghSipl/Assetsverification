package com.mahyco.cmr_app.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mahyco.assetsverification.api.IDataServiceCMR
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.*
import com.mahyco.assetsverification.login.model.LoginParam
import com.mahyco.assetsverification.login.model.LoginResponseModel
import io.reactivex.Observable
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImplCMRDataRepo(private val iDataServiceCMR: IDataServiceCMR) :
    CMRDataRepo {



    override fun callLoginAPi(
        loginParam: LoginParam
    ): Observable<LoginResponseModel> {
        return Observable.create {
            val apiCall = iDataServiceCMR.callLogin(loginParam)

            apiCall.enqueue(object : Callback<LoginResponseModel> {
                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                    val response = LoginResponseModel()
                    it.onNext(response)
                    it.onComplete()
                }

                override fun onResponse(
                    call: Call<LoginResponseModel>,
                    response: Response<LoginResponseModel>
                ) {
                    when {
                        response.isSuccessful -> {
                            val responseData = response.body()
                            if (responseData != null) {
                                it.onNext(responseData)
                                it.onComplete()
                            }
                        }
                        else -> {
                            val response = LoginResponseModel()
                           // response.success = false
                            it.onNext(response)
                            it.onComplete()
                        }
                    }
                }
            })
        }
    }

    override fun getAssetDetail(scanQRParam: ScanQRParam): Observable<ScanQRResult> {
        return Observable.create {
            val apiCall = iDataServiceCMR.getQrReport(scanQRParam)

            apiCall.enqueue(object : Callback<ScanQRResult> {
                override fun onFailure(call: Call<ScanQRResult>, t: Throwable) {
                    val response = ScanQRResult()
                    it.onNext(response)
                    it.onComplete()
                }

                override fun onResponse(
                    call: Call<ScanQRResult>,
                    response: Response<ScanQRResult>
                ) {
                    when {
                        response.isSuccessful -> {
                            val responseData = response.body()
                            if (responseData != null) {
                                it.onNext(responseData)
                                it.onComplete()
                            }
                        }
                        else -> {
                            val response = ScanQRResult()
                            // response.success = false
                            it.onNext(response)
                            it.onComplete()
                        }
                    }
                }
            })
        }
    }

    override fun SaveAssetStatus(saveAssetStatusParam: SaveAssetStatusParam): Observable<SaveStatusResponse> {
        return Observable.create {
            val apiCall = iDataServiceCMR.saveAssetStatus(saveAssetStatusParam)

            apiCall.enqueue(object : Callback<SaveStatusResponse> {
                override fun onFailure(call: Call<SaveStatusResponse>, t: Throwable) {
                    val response = SaveStatusResponse()
                    it.onNext(response)
                    it.onComplete()
                }

                override fun onResponse(
                    call: Call<SaveStatusResponse>,
                    response: Response<SaveStatusResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            val responseData = response.body()
                            if (responseData != null) {
                                it.onNext(responseData)
                                it.onComplete()
                            }
                        }
                        else -> {
                            val response = SaveStatusResponse()
                            // response.success = false
                            it.onNext(response)
                            it.onComplete()
                        }
                    }
                }
            })
        }
    }

    override fun checkUserValid(checkUserValidParam: CheckUserValidParam): Observable<CheckUserValidResponse> {
        return Observable.create {
            val apiCall = iDataServiceCMR.checkUserValid(checkUserValidParam)

            apiCall.enqueue(object : Callback<CheckUserValidResponse> {
                override fun onFailure(call: Call<CheckUserValidResponse>, t: Throwable) {
                    val response = CheckUserValidResponse()
                    it.onNext(response)
                    it.onComplete()
                }

                override fun onResponse(
                    call: Call<CheckUserValidResponse>,
                    response: Response<CheckUserValidResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            val responseData = response.body()
                            if (responseData != null) {
                                it.onNext(responseData)
                                it.onComplete()
                            }
                        }
                        else -> {
                            val response = CheckUserValidResponse()
                            // response.success = false
                            it.onNext(response)
                            it.onComplete()
                        }
                    }
                }
            })
        }
    }


}