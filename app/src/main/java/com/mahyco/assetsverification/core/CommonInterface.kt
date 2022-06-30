package com.mahyco.cmr_app.core

import android.content.Context

interface CommonInterface {

    fun showShortMessage(msg: String)
    fun isNetworkAvailable(context: Context): Boolean
    fun showLongMessage(msg: String)
    fun printLog(msg: String)
    fun getLocationData():String
    fun getLocationAccuracy():String
}