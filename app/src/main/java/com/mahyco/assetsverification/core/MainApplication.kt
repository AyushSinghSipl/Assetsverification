package com.mahyco.isp.core

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.mahyco.cmr_app.core.Constant
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject


@HiltAndroidApp
class MainApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    init {
        instance = this
    }

    companion object {
        var ctx: Context? = null
        var instance: MainApplication? = null

        fun applicationContext() : MainApplication {
            return instance as MainApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        // set value
        Constant.IS_DEBUGGABLE = CommonMethods.isDebuggable(applicationContext)
        Log.d("LOGS","IS_Debuggable : "+Constant.IS_DEBUGGABLE);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}