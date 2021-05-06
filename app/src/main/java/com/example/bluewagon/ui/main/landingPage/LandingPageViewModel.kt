package com.example.bluewagon.ui.main.landingPage

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.data.repo.RequestDeliveryRepository
import com.example.bluewagon.data.repo.TrackDeliveryReceiverRepository
import com.example.bluewagon.data.repo.TrackDeliveryRepository
import com.example.bluewagon.ui.main.track.TrackingPageActivity
import com.example.bluewagon.ui.main.track.response.TrackDeliveryResponse
import com.example.bluewagon.utils.ApiException
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.NoInternetException
import com.example.bluewagon.utils.SingleLiveEvent
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class LandingPageViewModel(val requestDeliveryRepository: RequestDeliveryRepository,val trackDeliveryRepository: TrackDeliveryRepository,val trackDeliveryReceiverRepository: TrackDeliveryReceiverRepository) :
    BaseViewModel() {

    var deliveryNo: String? = null
    var name: String? = null
    var contactNo: String? = null
    var dialog: Dialog? = null
    var trackDeliveryBySender:TrackDeliveryResponse?=null
    var trackDeliveryByReceiver:TrackDeliveryResponse?=null
    var trackEvent=SingleLiveEvent<Unit>()

    var authListenerInfo: AuthListenerInfo? = null
    var view:View?=null

    fun track(view: View) {
        if (deliveryNo?.length==0){
            authListenerInfo?.onInfo("Enter delivery No")
        }
        else if (name.isNullOrEmpty()){
            authListenerInfo?.onInfo("Enter name")
        }
        else if (contactNo.isNullOrEmpty()){
            authListenerInfo?.onInfo("Enter number")
        }
        else{
            authListenerInfo?.onStarted()
             trackEvent.call()
        }


    }

    fun requestDelivery(view: View) {
        dialog = Dialog(view.context)
        dialog!!.setContentView(R.layout.dialog_form)
        dialog!!.show()

        var address_sender = dialog!!.findViewById<EditText>(R.id.eTAddress_S)
        var address_receiver = dialog!!.findViewById<EditText>(R.id.eTAddress_R)
        var name_sender = dialog!!.findViewById<EditText>(R.id.etName_S)
        var name_reciever = dialog!!.findViewById<EditText>(R.id.etName_R)
        var number1_sender = dialog!!.findViewById<EditText>(R.id.et_contactno1_S)
        var number2_sender = dialog!!.findViewById<EditText>(R.id.et_contactno2_S)
        var number1_receiver = dialog!!.findViewById<EditText>(R.id.et_contactno1_R)
        var number2_receiver = dialog!!.findViewById<EditText>(R.id.et_contactno2_R)
        var request = dialog!!.findViewById<MaterialCardView>(R.id.cv_request)
        var remarks=dialog!!.findViewById<EditText>(R.id.et_Remarks)

        request.setOnClickListener {
            if (name_sender.text.isNullOrEmpty()){
                authListenerInfo?.onInfo("Enter sender name")
            }
           else if (address_sender.text.isNullOrEmpty()){
                authListenerInfo?.onInfo("Enter sender address")
            }
            else if (number1_sender.text.isNullOrEmpty()){
                authListenerInfo?.onInfo("Enter sender primary no")
            }
            else if (name_reciever.text.isNullOrEmpty()){
                authListenerInfo?.onInfo("Enter receiver name")
            }
            else if (address_receiver.text.isNullOrEmpty()){
                authListenerInfo?.onInfo("Enter receiver address")
            }
            else if (number1_receiver.text.isNullOrEmpty()){
                authListenerInfo?.onInfo("Enter receiver primary no")
            }
            else{
                viewModelScope.launch {
                    try {
                        var response = requestDeliveryRepository.requestDelivery(
                            name_sender.text.toString(),
                            address_sender.text.toString(),
                            number1_sender.text.toString(),
                            number2_sender.text.toString(),
                            name_reciever.text.toString(),
                            address_receiver.text.toString(),
                            number1_receiver.text.toString(),
                            number2_receiver.text.toString(),
                            remarks.text.toString(),
                            Constants.request
                        )

                        if (response.body()?.status=="SUCCESS"){
                            authListenerInfo?.onInfo(response.body()!!.message)
                            dialog!!.dismiss()
                        }
                        else{
                            authListenerInfo?.onInfo(response.body()!!.message)
                        }
                    } catch (e: ApiException) {
                        authListenerInfo?.onInfo(e.message!!)
                    } catch (e: NoInternetException) {
                        authListenerInfo?.onInfo(e.message!!)

                    }catch (e:NullPointerException){
                        authListenerInfo?.onInfo(e.message!!)
                    }
                }
            }



        }


    }


    fun trackDeliveryBySender(context: Context){

        viewModelScope.launch {

            try {
                var response=trackDeliveryRepository.trackDeliveryBySender(deliveryNo.toString(),name!!,contactNo!!)
                trackDeliveryBySender=response.body()

                if (trackDeliveryBySender?.status=="SUCCESS"){
                    Intent(context, TrackingPageActivity::class.java).also {
                        it.putExtra(Constants.role,Constants.sender)
                        it.putExtra(Constants.remarks,trackDeliveryBySender?.data?.remarks)
                        it.putExtra(Constants.deliveryNo,deliveryNo)
                        it.putExtra(Constants.name,name)
                        it.putExtra(Constants.contactNo,contactNo)
                        it.putExtra(Constants.senderName, trackDeliveryBySender?.data?.customerName)
                        it.putExtra(Constants.senderAddress, trackDeliveryBySender?.data?.address)
                        it.putExtra(Constants.senderPrimaryNo, trackDeliveryBySender?.data?.primaryContact)
                        it.putExtra(Constants.senderSecondaryNo, trackDeliveryBySender?.data?.secondaryContact)
                        it.putExtra(Constants.refNo, trackDeliveryBySender?.data?.refNo)
                        it.putExtra(Constants.receiverName, trackDeliveryBySender?.data?.receiverName)
                        it.putExtra(Constants.receiverAddress, trackDeliveryBySender?.data?.receiverAddress)
                        it.putExtra(Constants.receiverPrimaryNo, trackDeliveryBySender?.data?.receiverPrimaryContact)
                        it.putExtra(Constants.receiverSecondaryNo, trackDeliveryBySender?.data?.receiverSecondaryContact)
                        it.putExtra(Constants.paymentStatus, trackDeliveryBySender?.data?.orderStatus)
                        it.putExtra(Constants.lastLocation, trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.lastLocation)
                        it.putExtra(Constants.lastLocationTime, trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.time)
                        it.putExtra(Constants.lastLocationDate, trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.lastLocationDateFormat)
                        it.putExtra(Constants.userName,trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.userDTO?.name)
                        it.putExtra(Constants.userContactNo,trackDeliveryBySender?.data?.deliveryTrackHistoryDTO?.userDTO?.contactNumber)
                        context.startActivity(it)

                    }
                }


            }catch (e:ApiException){
                authListenerInfo?.onInfo(e.message!!)
            }catch (e:NoInternetException){
                authListenerInfo?.onInfo(e.message!!)
            }

        }
    }


    fun trackDeliveryByReceiver(context: Context){

        viewModelScope.launch {

            try {
                var response=trackDeliveryReceiverRepository.trackDeliveryByReceiver(deliveryNo.toString()!!,name!!,contactNo!!)
                trackDeliveryByReceiver=response.body()

                if (trackDeliveryByReceiver?.status=="SUCCESS"){


                    Intent(context, TrackingPageActivity::class.java).also {

                        it.putExtra(Constants.role,Constants.receiver)
                        it.putExtra(Constants.deliveryNo,deliveryNo)
                        it.putExtra(Constants.name,name)
                        it.putExtra(Constants.contactNo,contactNo)
                        it.putExtra(Constants.remarks,trackDeliveryByReceiver?.data?.remarks)
                        it.putExtra(Constants.senderName, trackDeliveryByReceiver?.data?.customerName)
                        it.putExtra(Constants.senderAddress, trackDeliveryByReceiver?.data?.address)
                        it.putExtra(Constants.senderPrimaryNo, trackDeliveryByReceiver?.data?.primaryContact)
                        it.putExtra(Constants.senderSecondaryNo, trackDeliveryByReceiver?.data?.secondaryContact)
                        it.putExtra(Constants.refNo, trackDeliveryByReceiver?.data?.refNo)
                        it.putExtra(Constants.receiverName, trackDeliveryByReceiver?.data?.receiverName)
                        it.putExtra(Constants.receiverAddress, trackDeliveryByReceiver?.data?.receiverAddress)
                        it.putExtra(Constants.receiverPrimaryNo, trackDeliveryByReceiver?.data?.receiverPrimaryContact)
                        it.putExtra(Constants.receiverSecondaryNo, trackDeliveryByReceiver?.data?.receiverSecondaryContact)
                        it.putExtra(Constants.paymentStatus, trackDeliveryByReceiver?.data?.orderStatus)
                        it.putExtra(Constants.lastLocation, trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.lastLocation)
                        it.putExtra(Constants.lastLocationTime, trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.time)
                        it.putExtra(Constants.lastLocationDate, trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.lastLocationDateFormat)
                        it.putExtra(Constants.userName,trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.userDTO?.name)
                        it.putExtra(Constants.userContactNo,trackDeliveryByReceiver?.data?.deliveryTrackHistoryDTO?.userDTO?.contactNumber)
                        context.startActivity(it)

                    }
                }


            }catch (e:ApiException){
                authListenerInfo?.onInfo(e.message!!)
            }catch (e:NoInternetException){
                authListenerInfo?.onInfo(e.message!!)
            }catch (e:NullPointerException){authListenerInfo?.onInfo(e.message!!)
            }

        }
    }
}