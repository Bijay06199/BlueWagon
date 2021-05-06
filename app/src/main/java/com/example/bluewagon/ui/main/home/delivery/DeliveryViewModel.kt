package com.example.bluewagon.ui.main.home.delivery

import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.repo.CompletedRepository
import com.example.bluewagon.data.repo.PendingRepository
import com.example.bluewagon.data.repo.ReceivedRepository
import com.example.bluewagon.data.repo.UpdateStatusRepository
import com.example.bluewagon.ui.main.home.delivery.response.DeliveryResponse
import com.example.bluewagon.utils.ApiException
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.NoInternetException
import com.example.bluewagon.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DeliveryViewModel(val pendingRepository: PendingRepository,val receivedRepository: ReceivedRepository,val completedRepository: CompletedRepository,private val updateStatusRepository: UpdateStatusRepository):BaseViewModel() {

    var pendingEvent=SingleLiveEvent<Unit>()
    var pendingResponse:DeliveryResponse?=null


    var authListenerInfo:AuthListenerInfo?=null

    fun getPendingReport(){
        viewModelScope.launch {
            authListenerInfo?.onStarted()
            try {
                var response=pendingRepository.getPending()
                pendingResponse=response.body()
                pendingEvent.call()
            }catch (e:NoInternetException){
                 authListenerInfo?.onInfo(e.localizedMessage)
            }catch (e:ApiException){
                authListenerInfo?.onInfo(e.localizedMessage)

            }
        }

    }


    fun getReceivedReport(){
        viewModelScope.launch {
            authListenerInfo?.onStarted()
            try {
                var response=receivedRepository.getReceived()
                pendingResponse=response.body()
                pendingEvent.call()
            }catch (e:NoInternetException){
                authListenerInfo?.onInfo(e.localizedMessage)
            }catch (e:ApiException){
                authListenerInfo?.onInfo(e.localizedMessage)

            }
        }
    }

    fun getCompletedReport(){
        viewModelScope.launch {
//            authListenerInfo?.onStarted()
            try {
                var response=completedRepository.getCompleted()
                pendingResponse=response.body()
                pendingEvent.call()
            }catch (e:NoInternetException){
                authListenerInfo?.onInfo(e.localizedMessage)
            }catch (e:ApiException){
                authListenerInfo?.onInfo(e.localizedMessage)

            }
        }
    }


    fun updateStatus(id:Int,deliveryStatus:String,reason:String,paymentStatus:String,remarks:String){

        viewModelScope.launch {
            try {
                updateStatusRepository.getUpdate(id, deliveryStatus, reason, paymentStatus, remarks)

            }catch (e:NoInternetException){}
            catch (e:ApiException){}
        }
    }
}