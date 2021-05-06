package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.main.home.response.CountResponse
import retrofit2.Response

class CountRepository(var apiServices: wagonApiServices,var preferenceManager: PreferenceManager) {

    suspend fun getCount():Response<CountResponse>{

        return apiServices.getCount(preferenceManager.getCustomerId()!!)

    }
}