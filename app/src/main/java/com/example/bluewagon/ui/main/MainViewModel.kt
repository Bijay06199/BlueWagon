package com.example.bluewagon.ui.main

import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.repo.LocationRepository
import kotlinx.coroutines.launch

class MainViewModel(private val locationRepository: LocationRepository):BaseViewModel() {




    fun currentLocation(location:String){

        viewModelScope.launch {
            locationRepository.getLocation(location,locationRepository.preferenceManager.getCustomerId()!!)
        }
    }
}