package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.auth.login.body.LocationBody
import com.example.bluewagon.ui.auth.login.response.LocationResponse
import retrofit2.Response

class LocationRepository(val apiServices: wagonApiServices,val preferenceManager: PreferenceManager) {
    suspend fun getLocation(location:String,id:Int):Response<LocationResponse>{

        val requestData=LocationBody(location,id)
        return apiServices.getLocation(requestData)
    }
}