package com.mahyco.assetsverification.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.mahyco.assetsverification.HomeActivity
import com.mahyco.assetsverification.MainActivity
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.core.Messageclass
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.assetsverification.databinding.ActivityLoginBinding
import com.mahyco.assetsverification.databinding.ActivityMainBinding
import com.mahyco.assetsverification.login.model.LoginParam
import com.mahyco.cmr_app.core.Constant
import com.mahyco.rcbucounterboys2020.utils.EncryptDecryptManager
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var device_Unique_id = ""
    var msclass: Messageclass? = null

    private val viewModel: LoginViewModel by viewModels()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        msclass = Messageclass(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getDeviceIMEI()
        }
        setUi()
        registerObserver()
    }

    private fun registerObserver() {
        viewModel!!.loadingLiveData.observe(this, androidx.lifecycle.Observer {
            if (it) {
                binding.llProgressBar.loader.visibility = View.VISIBLE
            } else {
                binding.llProgressBar.loader.visibility = View.GONE
            }
        })

        //In Case of error will show error in  toast message
        viewModel!!.errorLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.userData.observe(this, androidx.lifecycle.Observer {

            var result = it

            if (result.returnmsg == "User Successfully Login") {
                saveUserCode(this, result.accessToken)
                val sharedPreference: SharedPreference = SharedPreference(this)
                sharedPreference.save(Constant.IS_USER_LOGGED_IN, true)
                val userNameEncrypted =
                    "" + EncryptDecryptManager.encryptStringData(result?.empName)
                sharedPreference.save(Constant.USER_NAME, userNameEncrypted)
                sharedPreference.save(Constant.EMP_ID, binding.edtEmpCode.text.toString())
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                msclass?.showMessage("Invalid username and password!")
            }
        })
    }
    fun saveUserCode(context: Context, token: String?) {
        val empCode = "" + token
        EncryptDecryptManager.saveUserCodeWithEncryption(empCode, context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateData(): Boolean {

        if (binding.edtEmpCode.text.toString() == "") {
            msclass?.showMessage("Please enter emp code")
            return false
        }
        if (binding.edtPassword.text.toString() == "") {
            msclass?.showMessage("Please enter password")
            return false
        }
       /* if (device_Unique_id == "") {
            msclass?.showMessage("empty device id")
            getDeviceIMEI()
            return false
        }*/
        return true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUi() {

        binding.btnLogin.setOnClickListener {
//            startActivity(Intent(this,HomeActivity::class.java))
            if (validateData()) {
                callLogin()
            }
        }
    }

    private fun callLogin() {

//        val loginParam =
//            LoginParam(binding.edtEmpCode.text.toString(), device_Unique_id, binding.edtPassword.text.toString())
   val loginParam =
            LoginParam(  binding.edtPassword.text.toString(),binding.edtEmpCode.text.toString())

viewModel.callLoginApi(loginParam)


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDeviceIMEI() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                3000
            )
            return
        }
        var IMEINumber = ""
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IMEINumber = telephonyManager.imei
            }
            device_Unique_id = IMEINumber
        } catch (e: Exception) {
            val uniquePseudoID =
                "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10
            val serial = Build.getRadioVersion()
            val uuid: String =
                UUID(uniquePseudoID.hashCode().toLong(), serial.hashCode().toLong()).toString()
            val brand = Build.BRAND
            val modelno = Build.MODEL
            val version = Build.VERSION.RELEASE
            device_Unique_id = uuid
            Log.e(
                "IMEI", """fetchDeviceInfo: 
 
 uuid is : $uuid
 brand is: $brand
 model is: $modelno
 version is: $version"""
            )
        }
        Log.e("IMEI", "onCreate: " + IMEINumber)
        //  return IMEINumber
    }
}