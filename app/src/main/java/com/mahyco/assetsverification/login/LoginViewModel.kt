package com.mahyco.assetsverification.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.api.IDataServiceCMR
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
open class LoginViewModel  @Inject constructor(application: Application) : BaseViewModel(application) {
    private var mContext: Context = application
    private var iServiceISP: IDataServiceCMR

    var userData = MutableLiveData<LoginResponseModel>()


    init {
        var apiClient = RetrofitApiClient.getAPIClient()
        iServiceISP = apiClient.create(IDataServiceCMR::class.java)
    }

    fun callLoginApi(
        loginParam: LoginParam
    ){

        val sharedPreference: SharedPreference = SharedPreference(mContext)
        loadingLiveData.value = true
        val disposable = CompositeDisposable()
        val observable = ImplCMRDataRepo(iServiceISP).callLoginAPi(loginParam
        )
        val dispose = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                loadingLiveData.value = false

                if (response != null /*&& response.message!=null*/) {
//                    if (response.getVehicleTypeResponse != null) {
                    userData.value = response

                    sharedPreference.save(Constant.IS_USER_LOGGED_IN, true)
                    val userNameEncrypted =
                        "" + EncryptDecryptManager.encryptStringData(response?.empName)
                    sharedPreference.save(Constant.USER_NAME, userNameEncrypted)
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