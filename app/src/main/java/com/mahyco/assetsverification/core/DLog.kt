package com.mahyco.cmr_app.core

import android.util.Log

class DLog {

    companion object {
        val TAG = "InputSupplyProduction"

        /** Log Level Error  */
        fun e(message: String?) {
            if (Constant.IS_DEBUGGABLE) Log.e(TAG, buildLogMsg(message).toString())
        }

        /** Log Level Warning  */
        fun w(message: String?) {
            if (Constant.IS_DEBUGGABLE) Log.w(TAG, buildLogMsg(message).toString())
        }

        /** Log Level Information  */
        fun i(message: String?) {
            if (Constant.IS_DEBUGGABLE) Log.i(TAG, buildLogMsg(message).toString())
        }

        /** Log Level Debug  */
        fun d(message: String?) {
            if (Constant.IS_DEBUGGABLE) Log.d(TAG, buildLogMsg(message).toString())
        }

        /** Log Level Verbose  */
        fun v(message: String?) {
            if (Constant.IS_DEBUGGABLE) Log.v(TAG, buildLogMsg(message).toString())
        }


        fun buildLogMsg(message: String?): String? {
            val ste =
                Thread.currentThread().stackTrace[4]
            val sb = StringBuilder()
            sb.append("[")
            sb.append(ste.fileName.replace(".java", ""))
            sb.append("::")
            sb.append(ste.methodName)
            sb.append("]")
            sb.append(message)
            return sb.toString()
        }
    }
}