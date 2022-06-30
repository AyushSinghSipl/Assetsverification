package com.mahyco.cmr_app.core

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mahyco.assetsverification.R

class Constant {

    companion object {
        var IS_DEBUGGABLE = true
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
        const val USER_TOKEN = "user_token"
        const val TOKEN_TYPE = "token_type"
        const val FINAL_MESSAGE = "finalmessage"
        const val USER_NAME = "user_name"
        const val EMP_ID = "emp_id"

        const val HOME = "HOME"
        const val CANCEL_SCAN = "CANCEL SCAN"
        const val PROFILE = "PROFILE"
        const val SETTING = "SETTING"
        const val LOGOUT = "LOGOUT"

        const val SUCCESS_STATUS = "success"
        const val FAILED_STATUS =  "failed"
        const val SCANNED_DATA =  "scanned_data"

        const val CMR_ALTER = "cmr_alert"

        var queryImageUrl = ""
        var Imagename = ""
        var Imagename2 = ""
        var Imagename3 = ""
        var Imagename4 = ""
        var imageUri: Uri? = null

        const val PRE_KEY = "com.newtrail.mahyco.trail.utils.PREFERENCE_FILE_KEY"
        var ACCESS_TOKEN_TAG = "ACCESSTOKEN"
        var ACCESS_TOKEN_EXPIRY = "ACCESS_TOKEN_EXPIRY"
        var USER_CODE_TAG = "USERCODE"
        var USER_CODE_PREF = "USERCODE"
        var USER_PASSWORD_PREF = "USERCODE"
        var finalmessage = "finalmessage"
        var LOGIN_DATA = "login_data"
        var USER_CODE = "usercode"
        var VEHICLE_LIST = "vehicle_list"
        var ACTIVITY_LIST = "activity_list"
        var PASSWORD = "password"
        var IMEI = "imei"
        var RANDOM_CODE = "random_code"
        var TAG_TYPE = "tag_type"
        var NURSERY_TAG = "nursery_tag"
        var FULL_TAG = "full_tag"
        var NO_TAG = "no_tag"
        var FROM_INTENT = "from_intent"
        var TAG_AREA = "tag_area"
        var SOWN_STATUS = "upload"
        var PLD = "pld"
        var NOT_SOWN = "not_sown"
        var UPLOAD = "upload"
        fun isTimeAutomatic(c: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.Global.getInt(c.contentResolver, Settings.Global.AUTO_TIME, 0) === 1
            } else {
                Settings.System.getInt(c.contentResolver, Settings.System.AUTO_TIME, 0) == 1
            }
        }
        public fun addFragmentToActivity(
            fragment: Fragment?,
            container: Int,
            fragmentManager: FragmentManager,
            tag: String
        ){
            if (fragment == null) return
            val fm = fragmentManager
            val tr = fm.beginTransaction()
            tr.add(container, fragment)
            tr.addToBackStack(tag)
            tr.commit()
//        curFragment = fragment
        }

    }



}