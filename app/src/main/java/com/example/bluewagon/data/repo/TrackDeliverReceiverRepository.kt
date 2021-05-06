package com.example.bluewagon.data.repo

import com.example.bluewagon.constants.Constants
import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.main.track.response.TrackDeliveryResponse
import retrofit2.Response

class TrackDeliveryReceiverRepository(val apiServices: wagonApiServices, val preferenceManager: PreferenceManager) {



    suspend fun trackDeliveryByReceiver(
        deliveryNo:String,
        name:String,
        contactNo:String
    ): Response<TrackDeliveryResponse> {
        return apiServices.trackDeliveryByReceiver(deliveryNo,name,contactNo, Constants.organizationId)
    }
}