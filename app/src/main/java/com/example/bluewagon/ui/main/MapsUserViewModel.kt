package com.example.bluewagon.ui.main

import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.repo.TrackDeliveryRepository
import com.example.bluewagon.ui.main.track.response.TrackDeliveryResponse
import com.example.bluewagon.utils.ApiException
import com.example.bluewagon.utils.NoInternetException
import com.example.bluewagon.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MapsUserViewModel(val trackDeliveryRepository: TrackDeliveryRepository) : BaseViewModel() {


    var trackDeliveryBySender: TrackDeliveryResponse? = null
    var trackDeliveryByReceiver: TrackDeliveryResponse? = null
    var trackSenderEvent=SingleLiveEvent<Unit>()
    var trackReceiverEvent=SingleLiveEvent<Unit>()


    fun trackSender(deliverNo: String, name: String, contactNo: String) {

        viewModelScope.launch {
            try {
                var response = trackDeliveryRepository.trackDeliveryBySender(
                    deliverNo.toString(),
                    name,
                    contactNo
                )
                trackDeliveryBySender = response.body()
                trackSenderEvent.call()
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {
            }
        }
    }


    fun trackReceiver(deliverNo: String, name: String, contactNo: String) {

        viewModelScope.launch {
            try {
                var response = trackDeliveryRepository.trackDeliveryByReceiver(
                    deliverNo.toString(),
                    name,
                    contactNo
                )
                trackDeliveryByReceiver = response.body()
                trackReceiverEvent.call()
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {
            }
        }
    }


}