package com.mahyco.assetsverification.asset_verification.asset_detail.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.api.IDataServiceCMR
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.*
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.assetsverification.login.model.LoginParam
import com.mahyco.assetsverification.login.model.LoginResponseModel
import com.mahyco.cmr_app.core.Constant
import com.mahyco.cmr_app.repositories.ImplCMRDataRepo
import com.mahyco.isp.repositories.RetrofitApiClient
import com.mahyco.isp.viewmodel.BaseViewModel
import com.mahyco.rcbucounterboys2020.utils.EncryptDecryptManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
    private var mContext: Context = application
    private var iServiceISP: IDataServiceCMR
    init {
        var apiClient = RetrofitApiClient.getAPIClient()
        iServiceISP = apiClient.create(IDataServiceCMR::class.java)
    }

    var QRdata = MutableLiveData<ScanQRResult>()
    var SaveAssetStatusData = MutableLiveData<SaveStatusResponse>()
    var checkUserValidData = MutableLiveData<CheckUserValidResponse>()

    fun getAssetDetailsApi(
        scanQRParam: ScanQRParam
    ){

        val sharedPreference: SharedPreference = SharedPreference(mContext)
        loadingLiveData.value = true
        val disposable = CompositeDisposable()
        val observable = ImplCMRDataRepo(iServiceISP).getAssetDetail(scanQRParam
        )
        val dispose = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                loadingLiveData.value = false

                if (response != null /*&& response.message!=null*/) {
                        QRdata.value = response



                } else {
                    errorLiveData.value = mContext.resources.getString(R.string.server_error)
                }
            }

        disposable.add(dispose)
    }

    fun saveAssetStatus(
        saveAssetStatusParam: SaveAssetStatusParam
    ){

        val sharedPreference: SharedPreference = SharedPreference(mContext)
        loadingLiveData.value = true
        val disposable = CompositeDisposable()
        val observable = ImplCMRDataRepo(iServiceISP).SaveAssetStatus(saveAssetStatusParam
        )
        val dispose = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                loadingLiveData.value = false

                if (response != null /*&& response.message!=null*/) {
//                    if (response.getVehicleTypeResponse != null) {
                    SaveAssetStatusData.value = response


//                    sharedPreference.save(Constant.EMP_ID, edt_emp_code.text.toString())

//                    } else
                    //                    {
////                        errorLiveData.value = response.message
//                    }

                } else {
                    errorLiveData.value = mContext.resources.getString(R.string.server_error)
                }
            }

        disposable.add(dispose)
    }

    fun checkUserValidData(
        checkUserValidParam: CheckUserValidParam
    ){

        val sharedPreference: SharedPreference = SharedPreference(mContext)
        loadingLiveData.value = true
        val disposable = CompositeDisposable()
        val observable = ImplCMRDataRepo(iServiceISP).checkUserValid(checkUserValidParam
        )
        val dispose = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                loadingLiveData.value = false

                if (response != null /*&& response.message!=null*/) {
//                    if (response.getVehicleTypeResponse != null) {
                    checkUserValidData.value = response


//                    sharedPreference.save(Constant.EMP_ID, edt_emp_code.text.toString())

//                    } else
                    //                    {
////                        errorLiveData.value = response.message
//                    }

                } else {
                    errorLiveData.value = mContext.resources.getString(R.string.server_error)
                }
            }

        disposable.add(dispose)
    }
}