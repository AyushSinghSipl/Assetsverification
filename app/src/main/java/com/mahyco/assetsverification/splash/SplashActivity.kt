package com.mahyco.assetsverification.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mahyco.assetsverification.HomeActivity
import com.mahyco.assetsverification.MainActivity
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.assetsverification.login.LoginActivity
import com.mahyco.cmr_app.core.Constant

class SplashActivity : AppCompatActivity() {

//    val sharedPreference: SharedPreference = SharedPreference(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    val sharedPreference: SharedPreference = SharedPreference(this)
        Handler().postDelayed({
            if (sharedPreference.getValueBoolean(Constant.IS_USER_LOGGED_IN, false) != null) {
                val isLoggedIn =
                    sharedPreference.getValueBoolean(Constant.IS_USER_LOGGED_IN, false)!!
                if (isLoggedIn) {
                    //showLongMessage("Already logged in")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    //showLongMessage("Login")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
          /*  startActivity(Intent(this, LoginActivity::class.java))
                finish()*/

        }, 3000)
    }
}