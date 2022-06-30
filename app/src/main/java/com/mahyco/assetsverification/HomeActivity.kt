package com.mahyco.assetsverification

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.AppBarConfiguration
import com.budiyev.android.codescanner.*
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.mahyco.assetsverification.asset_verification.asset_detail.AssetDetailsFragment
import com.mahyco.assetsverification.core.Messageclass
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.assetsverification.databinding.ActivityHomeBinding
import com.mahyco.assetsverification.login.LoginActivity
import com.mahyco.cmr_app.core.Constant
import com.mahyco.rcbucounterboys2020.utils.EncryptDecryptManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityHomeBinding
    private lateinit var codeScanner: CodeScanner
    private val MY_CAMERA_REQUEST_CODE = 100
    var msclass: Messageclass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        codeScanner = CodeScanner(this, binding.appBarHome.contentHome.scannerView)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_asset_detail, R.id.nav_asset_status, R.id.nav_asset_verified
            ), drawerLayout
        )

        setUi()
    }

    private fun setUi() {
        binding.appBarHome.contentHome.btnAstVerification.setOnClickListener {
            scanQr()
        }
        binding.appBarHome.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START, true)
        }
        val sd = SimpleDateFormat(
            "hh:mm aa" +
                    " - EEE,dd MMM"
        )
        val currentDate = sd.format(Date())

        val textViewName =
            binding.navView.getHeaderView(0).findViewById(R.id.textViewUserName) as TextView
        val sharedPreference: SharedPreference = SharedPreference(this)
        val encryptedUserCode = sharedPreference.getValueString(Constant.USER_NAME)
        val decryptedUserCode = "" + EncryptDecryptManager.decryptStringData(encryptedUserCode)
        textViewName.text = decryptedUserCode

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.title.toString().lowercase()) {
                Constant.HOME.toString().toLowerCase() -> {
                    //write your implementation here
                    val fm: FragmentManager = getSupportFragmentManager()
                    for (i in 0 until fm.getBackStackEntryCount()) {
                        fm.popBackStack()
                    }

                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START, false)
                    }
                    true
                }

                Constant.CANCEL_SCAN.toString().toLowerCase() -> {
                    //write your implementation here
                    codeScanner.releaseResources()
                    binding.appBarHome.contentHome.scannerView.visibility = View.GONE
                    binding.appBarHome.contentHome.btnAstVerification.visibility = View.VISIBLE
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START, false)
                    }
                    true
                }
                Constant.SETTING.toString().toLowerCase() -> {
                    //write your implementation here
                    Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START, false)
                    }
                    true
                }
                Constant.PROFILE.toString().toLowerCase() -> {
                    //write your implementation here
                    Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START, false)
                    }
                    true
                }
                Constant.LOGOUT.toString().toLowerCase() -> {
                    //write your implementation here
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    logout()

                    true
                }
                else -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // menuInflater.inflate(R.menu.home, menu)
        return true
    }

    private fun logout() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Are you sure you want to logout")
//        alertDialog.setMessage("* Logout may wipe your travel and event data")
        alertDialog.setButton("Cancel") { dialog, which ->
            alertDialog.dismiss()
        }
        alertDialog.setButton("Logout") { dialog, which ->
            //        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            val sharedPreference: SharedPreference = SharedPreference(applicationContext)
            sharedPreference.save(Constant.IS_USER_LOGGED_IN, false)
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }

        // Showing Alert Message
        alertDialog.show()

    }

    private fun scanQr() {
        binding.appBarHome.contentHome.scannerView.visibility = View.VISIBLE
        binding.appBarHome.contentHome.btnAstVerification.visibility = View.GONE

        msclass = Messageclass(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                MY_CAMERA_REQUEST_CODE
            )
        } else {

            // Parameters (default values)
            codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
            codeScanner.isFlashEnabled = false // Whether to enable flash or not

            // Callbacks
            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {
//                    Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//                    startActivity(Intent(this, AssetDetailActivity::class.java))
                    Constant.addFragmentToActivity(
                        AssetDetailsFragment.newInstance(it.text),
                        R.id.container,
                        supportFragmentManager,
                        AssetDetailsFragment.TAG
                    )

                    binding.appBarHome.contentHome.scannerView.visibility = View.GONE
                    binding.appBarHome.contentHome.btnAstVerification.visibility = View.VISIBLE
                }
            }
            codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnUiThread {
                    Toast.makeText(
                        this, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


            binding.appBarHome.contentHome.scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.appBarHome.contentHome.scannerView.visibility == View.VISIBLE) {
            codeScanner.startPreview()
        }
    }

    override fun onBackPressed() {

        if (supportFragmentManager.getBackStackEntryCount() > 0) {

            supportFragmentManager.popBackStackImmediate()
            return
        } else
            if (binding.appBarHome.contentHome.scannerView.visibility == View.VISIBLE) {
                binding.appBarHome.contentHome.scannerView.visibility = View.GONE
                binding.appBarHome.contentHome.btnAstVerification.visibility = View.VISIBLE
                return
            } else if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START, false)
//                binding.drawerLayout.closeDrawer(binding.appBarHome.contentHome.recyclerViewNavigation,true)
                return
            } else {
                finish()
            }
        super.onBackPressed()

    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                codeScanner.camera =
                    CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
                codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
                // ex. listOf(BarcodeFormat.QR_CODE)
                codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
                codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
                codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
                codeScanner.isFlashEnabled = false // Whether to enable flash or not

                // Callbacks
                codeScanner.decodeCallback = DecodeCallback {
                    runOnUiThread {
                        Constant.addFragmentToActivity(
                            AssetDetailsFragment.newInstance(it.text),
                            R.id.container,
                            supportFragmentManager,
                            AssetDetailsFragment.TAG
                        )
                        binding.appBarHome.contentHome.scannerView.visibility = View.GONE
                        binding.appBarHome.contentHome.btnAstVerification.visibility = View.VISIBLE
                    }
                }
                codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                    runOnUiThread {
                        Toast.makeText(
                            this, "Camera initialization error: ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


                binding.appBarHome.contentHome.scannerView.setOnClickListener {
                    codeScanner.startPreview()
                }
            }
        } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            msclass?.showMessage("camera permission denied")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            var result = IntentIntegrator.parseActivityResult(resultCode, data)
            if (result != null) {
                Toast.makeText(this, "scan complete ", Toast.LENGTH_SHORT).show()
                /* val intent: Intent = Intent(this, ScannAssetsActivity::class.java)
                 startActivity(intent)*/
                Constant.addFragmentToActivity(
                    AssetDetailsFragment(),
                    R.id.container,
                    supportFragmentManager,
                    AssetDetailsFragment.TAG
                )
                binding.appBarHome.contentHome.scannerView.visibility = View.GONE
                binding.appBarHome.contentHome.btnAstVerification.visibility = View.VISIBLE
            }
        }
    }
}