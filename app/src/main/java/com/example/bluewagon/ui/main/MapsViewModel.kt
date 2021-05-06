package com.example.bluewagon.ui.main

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.repo.RidersLocationRepository
import com.example.bluewagon.di.ridersLocationRepository
import com.example.bluewagon.ui.auth.login.LoginActivity
import com.example.bluewagon.ui.main.response.Data
import com.example.bluewagon.ui.main.response.RidersLocationResponse
import com.example.bluewagon.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MapsViewModel(   var ridersLocationRepository:RidersLocationRepository): BaseViewModel() {


    var locationEvent=SingleLiveEvent<Unit>()
    var ridersResponse:RidersLocationResponse?=null


    fun getRidersLocation(id:String){

        viewModelScope.launch {
            var response=ridersLocationRepository.getRidersLocation(id)
              ridersResponse=response.body()
              locationEvent.call()
        }
    }

    fun logout(view: View){
        ridersLocationRepository.preferenceManager.setIsLoggedIn(false)
        Intent(view.context,LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}