package com.example.bluewagon.data.repo

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.ui.auth.login.body.LoginBody
import com.example.bluewagon.ui.auth.login.response.LoginResponse
import com.raisetech.ecalculo.zorbistore.data.network.SafeApiRequest

class LoginRepository(private val apiServices: wagonApiServices,val preferenceManager: PreferenceManager):SafeApiRequest() {

    suspend fun login(
        email:String,
        password:String
    ):LoginResponse{

        val requestData=LoginBody(password,email)

        return apiRequest { apiServices.login(requestData) }
    }
}