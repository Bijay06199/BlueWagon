package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.auth.login.body.LoginBody
import com.example.bluewagon.ui.auth.login.response.LoginResponse
import com.example.bluewagon.ui.main.home.resetPassword.body.ResetPasswordBody
import com.example.bluewagon.ui.main.home.resetPassword.response.ResetPasswordResponse
import com.raisetech.ecalculo.zorbistore.data.network.SafeApiRequest
import okhttp3.ResponseBody

class ResetPasswordRepository(private val apiServices: wagonApiServices, val preferenceManager: PreferenceManager):
    SafeApiRequest() {

    suspend fun resetPassword(
         oldPassword:String,
         newPassword:String
    ): ResetPasswordResponse {

        val requestData= ResetPasswordBody(oldPassword,newPassword)

        return apiRequest { apiServices.resetPassword(preferenceManager.getCustomerId()!!,requestData) }
    }
}