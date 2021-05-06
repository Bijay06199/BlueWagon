package com.example.bluewagon.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.ActivityMapsBinding
import com.example.bluewagon.ui.auth.login.LoginActivity
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MapsActivity : BaseActivity<ActivityMapsBinding, MapsViewModel>() {

    lateinit var map: MapView
    lateinit var mapController: IMapController
    var location: String? = null
    var title: String? = null
    var part1: String? = null
    var part2: String? = null
    var i: Int = 0
    var lastLocation:String?=null
    var lastLocationTime:String?=null
    var lastLocationDate:String?=null
    var userName:String?=null
    var userNumber:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        var ctx = applicationContext

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        var tile=intent.getStringExtra(Constants.admin)


        showMap()
    }





    fun logout(view:View){

        preferenceManager.setIsLoggedIn(false)
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }



    private fun showMap() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            initialiseMaps()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
    }


    private fun initialiseMaps() {
        with(viewDataBinding) {


            val handler = Handler()

            val updateTask: Runnable = object : Runnable {
                override fun run() {
                   mapsViewModel.getRidersLocation(preferenceManager.getOrganizationId())
                    handler.postDelayed(this, 10000)
                }
            }

            handler.postDelayed(updateTask, 10000)

            map = findViewById(R.id.mapview)

            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setBuiltInZoomControls(true)
            map.setMultiTouchControls(true)
            mapController = map.controller
            mapController.setZoom(15)

            mapsViewModel.getRidersLocation(preferenceManager.getOrganizationId())


            mapsViewModel.locationEvent.observe(this@MapsActivity, Observer {


                mapsViewModel.ridersResponse?.data?.forEach { i ->
                    location = i.lastLocation
                    var userDto = i.userDTO
                    var time=i.time
                    title = i.userDTO.name



                    var str = location
                    var parts =
                        str?.split((",").toRegex())?.dropLastWhile({ it.isEmpty() })?.toTypedArray()
                    part1 = parts?.get(0)
                    part2 = parts?.get(1)


                    var startPoint = GeoPoint(part1!!.toDouble(), part2!!.toDouble())


                    mapController.setCenter(startPoint)

                    var startMarker = Marker(map)

                    startMarker.title = "Name:"+" "+i.userDTO.name+"\n"+"Last LocationDate:"+" "+i.lastLocationDateFormat+"\n "+"Time:"+" "+i.time+"\n "+"Contact:"+"\n "+i.userDTO.contactNumber
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    startMarker.setIcon(resources.getDrawable(R.drawable.bike))
                    map.overlays.add(startMarker)
                    startMarker.position = startPoint
//                    map.invalidate()


                }


            })


        }

    }

//    override fun onResume() {
//        super.onResume()
//        //this will refresh the osmdroid configuration on resuming.
//        //if you make changes to the configuration, use
//        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
//        if (map != null) map.onResume() //needed for compass, my location overlays, v6.0.0 and up
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                initialiseMaps()
            }
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

    override fun getLayoutId(): Int = R.layout.activity_maps

    override fun getViewModel(): MapsViewModel = mapsViewModel
    private val mapsViewModel: MapsViewModel by viewModel()
}