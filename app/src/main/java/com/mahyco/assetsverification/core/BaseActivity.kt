package com.mahyco.cmr_app.core

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mahyco.assetsverification.R

open class BaseActivity : AppCompatActivity(), CommonInterface {

    lateinit var alertCallback:AlertCallback



    override fun showShortMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isNetworkAvailable(context: Context): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun showLongMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun printLog(msg: String) {
        DLog.d(msg)
    }

    override fun getLocationData(): String {
        return ""
    }

    override fun getLocationAccuracy(): String {
        return ""
    }

    fun showAlert(message: String, context: Context, fromWhere:String,alertCallback1: AlertCallback) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(context.resources.getString(R.string.app_name_long))
        builder.setMessage(message)
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            alertCallback = alertCallback1
            alertCallback.getAlertCallback(fromWhere)
        }
        builder.show()
    }

    interface AlertCallback{
        fun getAlertCallback(fromWhere:String)
    }

    public fun getAppVersion(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }

    public fun startActivityThis(intent: Intent){
        startActivity(intent)
        /*Log.d("START_ACTIVITY","CONDITION getCallingActivity()?.getPackageName()): "+(activity.callingActivity?.packageName))
        Log.d("START_ACTIVITY","CONDITION BuildConfig.APPLICATION_ID: "+BuildConfig.APPLICATION_ID)
        Log.d("START_ACTIVITY","CONDITION : "+(activity.callingActivity?.packageName.equals(BuildConfig.APPLICATION_ID)))
        if (activity.callingActivity?.packageName.equals(BuildConfig.APPLICATION_ID)) {
            startActivity(intent)
        }*/
    }

   /* public fun addFragmentToActivity(fragment: Fragment?){
        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.add(R.id.container, fragment)
        tr.addToBackStack(null)
        tr.commitAllowingStateLoss()
//        curFragment = fragment
    }*/
    public fun replaceFragmentToActivity(fragment: Fragment?){
        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.replace(R.id.container, fragment)
        tr.addToBackStack(null)
        tr.commitAllowingStateLoss()
//        curFragment = fragment
    }
}