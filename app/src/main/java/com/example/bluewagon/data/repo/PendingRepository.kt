package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.main.home.delivery.response.DeliveryResponse
import retrofit2.Response

class PendingRepository(var apiServices: wagonApiServices,var preferenceManager: PreferenceManager) {
    suspend fun getPending():Response<DeliveryResponse>{
        return apiServices.getPending(preferenceManager.getCustomerId()!!)
    }
}