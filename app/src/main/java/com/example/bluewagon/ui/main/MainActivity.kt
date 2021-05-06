package com.example.bluewagon.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.ui.main.home.HomeFragment
import com.example.bluewagon.ui.main.home.delivery.DeliveryFragment
import com.example.bluewagon.utils.GPSTracker
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<com.example.bluewagon.databinding.ActivityMainBinding, MainViewModel>() {

    override fun getLayoutId(): Int =R.layout.activity_main
    override fun getViewModel(): MainViewModel =mainViewModel
    private val mainViewModel:MainViewModel by viewModel()
    lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState==null){
            HomeFragment.start(this, R.id.main_screen_container)
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var hasNetwork=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasNetwork){
            postLocation()
            initView()
        }
        else{
            initView()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }



    }

    private fun initView() {
        with(viewDataBinding){

            bottomNavigation.setOnNavigationItemSelectedListener { item->
                when(item.itemId){
                    R.id.page_1 -> {
                        HomeFragment.start(this@MainActivity, R.id.main_screen_container)
                        true
                    }
                    R.id.page_2 -> {
                        DeliveryFragment.start(
                            this@MainActivity,
                            R.id.main_screen_container,
                            Constants.pending
                        )
                        true
                    }
                    R.id.page_3 -> {
                        DeliveryFragment.start(
                            this@MainActivity,
                            R.id.main_screen_container,
                            Constants.received
                        )
                        true
                    }
                    R.id.page_4 -> {
                        DeliveryFragment.start(
                            this@MainActivity,
                            R.id.main_screen_container,
                            Constants.completed
                        )
                        true
                    }

                    else->false
                }
            }

        }
    }

    private fun postLocation() {
        with(viewDataBinding){

            val handler = Handler()

            val updateTask: Runnable = object : Runnable {
                override fun run() {
                    knowCurrentLocation()
                    handler.postDelayed(this, 5000)
                }
            }

            handler.postDelayed(updateTask, 5000)

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                knowCurrentLocation()
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
    }




    private fun knowCurrentLocation() {
        val gps= GPSTracker(this)
        val latitude:Double=gps.getLatitude()
        val longitude:Double=gps.getLongitude()

        val sb=StringBuilder()
        sb.append(latitude).append(",").append(longitude)



        with(mainViewModel){
            currentLocation(sb.toString())
        }
    }


    override fun onBackPressed() {

        if (preferenceManager.getIsLoggedIn()==true){
            finishAffinity()
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    knowCurrentLocation()
            }
        }
    }
}