package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.main.home.delivery.response.DeliveryResponse
import com.example.bluewagon.ui.main.home.resetPassword.response.ResetPasswordResponse
import retrofit2.Response

class UpdateStatusRepository(private val apiServices: wagonApiServices,private val preferenceManager: PreferenceManager) {

    suspend fun getUpdate(id:Int,deliveryStatus:String,reason:String,paymentStatus:String,remarks:String): Response<ResetPasswordResponse> {
        return apiServices.updateStatus(id,deliveryStatus,reason, paymentStatus, remarks)
    }
}