package com.example.bluewagon.ui.main.home

import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.repo.CountRepository
import com.example.bluewagon.data.repo.PendingRepository
import com.example.bluewagon.data.repo.UpdateStatusRepository
import com.example.bluewagon.di.updateStatusRepository
import com.example.bluewagon.ui.main.home.delivery.response.DeliveryResponse
import com.example.bluewagon.ui.main.home.response.CountResponse
import com.example.bluewagon.ui.main.home.response.Data
import com.example.bluewagon.utils.ApiException
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.NoInternetException
import com.example.bluewagon.utils.SingleLiveEvent
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pendingRepository: PendingRepository,
    private val updateStatusRepository: UpdateStatusRepository,
    private val countRepository: CountRepository
) : BaseViewModel() {


    var pendingEvent = SingleLiveEvent<Unit>()
    var pendingResponse: DeliveryResponse? = null
    var authListenerInfo: AuthListenerInfo? = null
    var countResponse: CountResponse? = null
    var countEvent = SingleLiveEvent<Unit>()

    fun getPendingReport() {
        viewModelScope.launch {
            authListenerInfo?.onStarted()
            try {
                var response = pendingRepository.getPending()
                pendingResponse = response.body()
                pendingEvent.call()
            } catch (e: NoInternetException) {
                authListenerInfo?.onInfo(e.localizedMessage)
            } catch (e: ApiException) {
                authListenerInfo?.onInfo(e.localizedMessage)

            }
        }

    }

    fun updateStatus(
        id: Int,
        deliveryStatus: String,
        reason: String,
        paymentStatus: String,
        remarks: String
    ) {

        viewModelScope.launch {
            try {
                updateStatusRepository.getUpdate(id, deliveryStatus, reason, paymentStatus, remarks)

            } catch (e: NoInternetException) {
            } catch (e: ApiException) {
            }
        }
    }

    fun getCount() {
        viewModelScope.launch {
            try {
                var response = countRepository.getCount()
                countResponse = response.body()
                countEvent.call()

            } catch (e: NoInternetException) {
            } catch (e: ApiException) {
            }
        }
    }

}