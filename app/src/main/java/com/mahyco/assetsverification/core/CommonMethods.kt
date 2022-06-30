package com.mahyco.isp.core

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class CommonMethods {
    companion object {
        public fun isDebuggable(context: Context): Boolean {
            var debuggable = false
            val pm: PackageManager = context.getPackageManager()
            try {
                val appinfo = pm.getApplicationInfo(context.getPackageName(), 0)
                debuggable = 0 != appinfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
            } catch (e: PackageManager.NameNotFoundException) {
                /* debuggable variable will remain false */
            }
            return debuggable
        }
    }
}