package com.example.bluewagon.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.ActivityMapsUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapsUserActivity :BaseActivity<ActivityMapsUserBinding,MapsUserViewModel>() {


    lateinit var map: MapView
    lateinit var mapController: IMapController
    var title: String? = null
    var part1: String? = null
    var part2: String? = null
    var i: Int = 0
    var lastLocation:String?=null
    var lastLocationTime:String?=null
    var lastLocationDate:String?=null
    var userName:String?=null
    var userNumber:String?=null
    var deliveryNo:String?=null
    var name:String?=null
    var contactNo:String?=null
    var role:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        var ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_maps_user)

        lastLocation=intent.getStringExtra(Constants.lastLocation)
        lastLocationDate=intent.getStringExtra(Constants.lastLocationDate)
        lastLocationTime=intent.getStringExtra(Constants.lastLocationTime)
        userName=intent.getStringExtra(Constants.userName)
        userNumber=intent.getStringExtra(Constants.userContactNo)
        role=intent.getStringExtra(Constants.role)
         deliveryNo=intent.getStringExtra(Constants.deliveryNo)
         name=intent.getStringExtra(Constants.name)
         contactNo=intent.getStringExtra(Constants.contactNo)



        showMap()
    }

    private fun senderMaps() {
        with(viewDataBinding){
            with(mapsUserViewModel){


                val handler = Handler()

                val updateTask: Runnable = object : Runnable {
                    override fun run() {
                        trackSender(deliveryNo!!,name!!,contactNo!!)
                        handler.postDelayed(this, 5000)
                    }
                }

                handler.postDelayed(updateTask, 5000)

                trackSender(deliveryNo!!,name!!,contactNo!!)
                trackSenderEvent.observe(this@MapsUserActivity, Observer {

                    var location=trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.lastLocation
                    var date=trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.lastLocationDateFormat
                    var time=trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.time
                    var userName=trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.userDTO?.name
                    var number=trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.userDTO?.contactNumber


                    var str = location
                    var parts =
                        str?.split((",").toRegex())?.dropLastWhile({ it.isEmpty() })?.toTypedArray()
                    part1 = parts?.get(0)
                    part2 = parts?.get(1)


                    var startPoint = GeoPoint(part1!!.toDouble(), part2!!.toDouble())


                    mapController.setCenter(startPoint)

                    var startMarker = Marker(map)

                    startMarker.title = "Name:"+" "+userName+"\n"+"Last LocationDate:"+" "+date+"\n "+"Time:"+" "+time+"\n "+"Contact:"+"\n "+number
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    startMarker.setIcon(resources.getDrawable(R.drawable.bike))
                    map.overlays.add(startMarker)
                    startMarker.position = startPoint

                })
            }
        }
    }

    private fun receiverMaps() {
        with(viewDataBinding){
            with(mapsUserViewModel){


                val handler = Handler()

                val updateTask: Runnable = object : Runnable {
                    override fun run() {
                        trackReceiver(deliveryNo!!,name!!,contactNo!!)
                        handler.postDelayed(this, 5000)
                    }
                }

                handler.postDelayed(updateTask, 5000)


                trackReceiver(deliveryNo!!,name!!,contactNo!!)
                trackReceiverEvent.observe(this@MapsUserActivity, Observer {
                    var location=trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.lastLocation
                    var date=trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.lastLocationDateFormat
                    var time=trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.time
                    var userName=trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.userDTO?.name
                    var number=trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.userDTO?.contactNumber


                    var str = location
                    var parts =
                        str?.split((",").toRegex())?.dropLastWhile({ it.isEmpty() })?.toTypedArray()
                    part1 = parts?.get(0)
                    part2 = parts?.get(1)


                    var startPoint = GeoPoint(part1!!.toDouble(), part2!!.toDouble())


                    mapController.setCenter(startPoint)

                    var startMarker = Marker(map)

                    startMarker.title = "Name:"+" "+userName+"\n"+"Last LocationDate:"+" "+date+"\n "+"Time:"+" "+time+"\n "+"Contact:"+"\n "+number
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    startMarker.setIcon(resources.getDrawable(R.drawable.bike))
                    map.overlays.add(startMarker)
                    startMarker.position = startPoint
                })
            }
        }
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
        map = findViewById(R.id.mapview)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        mapController = map.controller
        mapController.setZoom(15)


        when (role) {
            Constants.receiver -> {

                receiverMaps()
            }
            Constants.sender -> {
                senderMaps()
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun getLayoutId(): Int =R.layout.activity_maps_user

    override fun getViewModel(): MapsUserViewModel =mapsUserViewModel
    private val mapsUserViewModel:MapsUserViewModel by viewModel()


}