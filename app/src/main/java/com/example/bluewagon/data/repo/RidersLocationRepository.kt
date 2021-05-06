package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.main.response.RidersLocationResponse
import retrofit2.Response

class RidersLocationRepository(val apiServices: wagonApiServices,var preferenceManager: PreferenceManager) {

    suspend fun getRidersLocation(id:String):Response<RidersLocationResponse>{
        return apiServices.getRidersLocation(id)
    }
}