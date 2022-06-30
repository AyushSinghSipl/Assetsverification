package com.mahyco.assetsverification

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.integration.android.IntentIntegrator
import com.mahyco.assetsverification.asset_verification.asset_detail.AssetDetailsFragment
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.AssetStatusFragment
import com.mahyco.assetsverification.core.Messageclass
import com.mahyco.assetsverification.databinding.ActivityMainBinding
import com.mahyco.assetsverification.side_menu.NavigationDrawerAdapter
import com.mahyco.assetsverification.side_menu.model.SideMenuModel
import com.mahyco.assetsverification.side_menu.onMenuItemClick
import com.mahyco.cmr_app.core.BaseActivity
import com.mahyco.cmr_app.core.Constant


class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var codeScanner: CodeScanner
    private val MY_CAMERA_REQUEST_CODE = 100
    var msclass: Messageclass? = null
    var menuList = ArrayList<SideMenuModel>()
    lateinit var adapter: NavigationDrawerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        codeScanner = CodeScanner(this, binding.scannerView)
        setUi()
        msclass = Messageclass(this)
    }

    private fun setUi() {
        binding.btnAstVerification.setOnClickListener {
            /*  val intent: Intent = Intent(this, ScannAssetsActivity::class.java)
              startActivity(intent)*/

            scanQr()
        }

        binding.imgMenu.setOnClickListener {
//            binding.drawerLayout.openDrawer(Gravity.LEFT)
            binding.drawerLayout.openDrawer(binding.recyclerViewNavigation,true)
        }
        menuList.add(SideMenuModel(R.drawable.ic_home, Constant.HOME, true))
        menuList.add(SideMenuModel(R.drawable.ic_cancel_scan, Constant.CANCEL_SCAN, false))
        menuList.add(SideMenuModel(R.drawable.ic_profile, Constant.PROFILE, false))
        menuList.add(SideMenuModel(R.drawable.ic_setting, Constant.SETTING, false))
        menuList.add(SideMenuModel(R.drawable.ic_logout, Constant.LOGOUT, false))
        configureToolbar()

        adapter = NavigationDrawerAdapter(this, menuList, object : onMenuItemClick {
            override fun onItemSelect(position: Int) {
                for (item in menuList) {
                    if (menuList.get(position) == item) {
                        menuList.get(position).isSelected = true
                    } else {
                        menuList.get(position).isSelected = false
                    }

                }
                adapter.notifyDataSetChanged()
            }
        })
        binding.recyclerViewNavigation.adapter = adapter
        binding.recyclerViewNavigation.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun scanQr() {
        binding.scannerView.visibility = View.VISIBLE
        binding.btnAstVerification.visibility = View.GONE



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
                        AssetDetailsFragment(),
                        R.id.container,
                        supportFragmentManager,
                        AssetDetailsFragment.TAG
                    )

                    binding.scannerView.visibility = View.GONE
                    binding.btnAstVerification.visibility = View.VISIBLE
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


            binding.scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.scannerView.visibility == View.VISIBLE) {
            codeScanner.startPreview()
        }
    }

    override fun onBackPressed() {

        if (supportFragmentManager.getBackStackEntryCount() > 0) {

            supportFragmentManager.popBackStackImmediate()
            return
        } else
            if (binding.scannerView.visibility == View.VISIBLE) {
                binding.scannerView.visibility = View.GONE
                binding.btnAstVerification.visibility = View.VISIBLE
                return
            } else if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                binding.drawerLayout.closeDrawer(GravityCompat.START, false)
                binding.drawerLayout.closeDrawer(binding.recyclerViewNavigation,true)
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
                            AssetDetailsFragment(),
                            R.id.container,
                            supportFragmentManager,
                            AssetDetailsFragment.TAG
                        )
                        binding.scannerView.visibility = View.GONE
                        binding.btnAstVerification.visibility = View.VISIBLE
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


                binding.scannerView.setOnClickListener {
                    codeScanner.startPreview()
                }
            }
        } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            msclass?.showMessage("camera permission denied")
        }
    }


    private fun configureToolbar() {

        setSupportActionBar(binding.toolbar)
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
                binding.scannerView.visibility = View.GONE
                binding.btnAstVerification.visibility = View.VISIBLE
            }
        }
    }


}